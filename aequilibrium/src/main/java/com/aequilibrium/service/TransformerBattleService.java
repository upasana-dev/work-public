package com.aequilibrium.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aequilibrium.dto.TransformerWarResultDto;
import com.aequilibrium.dto.TransformerWarTeamResultDto;
import com.aequilibrium.model.Transformer;
import com.aequilibrium.model.constants.TransformerType;
import com.aequilibrium.repository.TransformerRepository;

@Service
public class TransformerBattleService {

	private static final int WIN_THRESHOLD_SKILLS = 3;
	private static final int WIN_THRESHOLD_COURAGE = 4;
	private static final int WIN_THRESHOLD_STRENGTH = 3;

	private static final List<String> ALL_POWERFUL = Arrays.asList("Optimus Prime", "Predaking");

	Logger logger = LoggerFactory.getLogger(TransformerBattleService.class);

	@Autowired
	private TransformerRepository transformerRepository;

	/**
	 * Simulates and computes the outcome of a Transformer war based on list of
	 * participants ({@code transformerNames} provided
	 * 
	 * <p>
	 * The outcome is computed by identifying the transformers from the list of
	 * names provided and determining the outcomes of the battles between these
	 * transformers based on a list of pre-defined rules. </br></br>
	 * If the name of an unknown transformer is provided, the entry is simply
	 * excluded from the computation
	 * </p>
	 * 
	 * @param transformerNames List of transformer names
	 * 
	 * @return The outcome of the war as {@link TransformerWarResultDto}
	 */
	public TransformerWarResultDto computeWar(List<String> transformerNames) {

		List<Transformer> transformerArmy = transformerRepository.findByNameIn(transformerNames);

		List<Transformer> autobots = new ArrayList<Transformer>();
		List<Transformer> decepticons = new ArrayList<Transformer>();

		// Classifying the transformers
		divideIntoTeams(transformerArmy, autobots, decepticons);

		// Sorting the transformers
		autobots.sort(Comparator.comparing(Transformer::getRank).reversed());
		decepticons.sort(Comparator.comparing(Transformer::getRank).reversed());

		return computeWarResult(autobots, decepticons);
	}

	TransformerWarResultDto computeWarResult(List<Transformer> autobots, List<Transformer> decepticons) {
		int autobotWinCount = 0;
		int decepticonWinCount = 0;

		int battleCount = 0;

		int potentialBattleCount = computePotentialBattleCount(autobots.size(), decepticons.size());

		Transformer battlingAutobot;
		Transformer battlingDecepticon;

		Transformer victor;

		List<String> survivingAutobots = new ArrayList<>();
		List<String> survivingDecepticons = new ArrayList<>();

		boolean isAllPowerfulAutobot = false;
		boolean isAllPowerfulDecepticon = false;

		for (int count = 0; count < potentialBattleCount; count++) {

			battlingAutobot = autobots.get(count);
			battlingDecepticon = decepticons.get(count);

			isAllPowerfulAutobot = isAllPowerful(battlingAutobot.getName());
			isAllPowerfulDecepticon = isAllPowerful(battlingDecepticon.getName());

			if (isAllPowerfulAutobot && isAllPowerfulDecepticon) {
				logger.warn("Armageddon! Battle between the all-powerfuls encountered, terminating war");
				battleCount++;
				// Destroying all competitors
				return constructWarResult(battleCount, new ArrayList<String>(), new ArrayList<String>(),
						"War terminated");
			}

			victor = determineVictor(battlingAutobot, battlingDecepticon, isAllPowerfulAutobot,
					isAllPowerfulDecepticon);

			if (victor == null) {
				logger.warn("Battle between autobot {} and decepticon {} was a draw. Both destroyed");
				battleCount++;
				continue;
			}

			if (TransformerType.A.equals(victor.getTransformerType())) {
				autobotWinCount++;
				survivingAutobots.add(victor.getName());
			} else if (TransformerType.D.equals(victor.getTransformerType())) {
				decepticonWinCount++;
				survivingDecepticons.add(victor.getName());
			}

			battleCount++;
		}

		// Include transformers with no matches as survivors
		if (battleCount < autobots.size()) {
			survivingAutobots.addAll(extractExcludedSurvivors(autobots, battleCount));
		} else if (battleCount < decepticons.size()) {
			survivingDecepticons.addAll(extractExcludedSurvivors(decepticons, battleCount));
		}

		return constructWarResult(battleCount, survivingAutobots, survivingDecepticons,
				determineWinningTeam(autobotWinCount, decepticonWinCount));
	}

	boolean isAllPowerful(String transformerName) {
		return ALL_POWERFUL.contains(transformerName);
	}

	List<String> extractExcludedSurvivors(List<Transformer> autobots, int battleCount) {
		return autobots.subList(battleCount, autobots.size()).stream().map(t -> t.getName())
				.collect(Collectors.toList());
	}

	Transformer determineVictor(Transformer battlingAutobot, Transformer battlingDecepticon,
			boolean isAllPowerfulAutobot, boolean isAllPowerfulDecepticon) {
		Transformer victor;
		if (isAllPowerfulAutobot) {
			victor = battlingAutobot;
		} else if (isAllPowerfulDecepticon) {
			victor = battlingDecepticon;
		} else {
			// Neither of the battling Transformers are all-powerful, so the outcome of the
			// battle will be computed based on the usual rules
			victor = determineVictor(battlingAutobot, battlingDecepticon);
		}
		return victor;
	}

	TransformerWarResultDto constructWarResult(int battleCount, List<String> survivingAutobots,
			List<String> survivingDecepticons, String winningTeam) {
		// Autobots team results
		TransformerWarTeamResultDto autobotsTeamResult = new TransformerWarTeamResultDto(TransformerType.A.getLabel(),
				survivingAutobots);

		// Decepticons team results
		TransformerWarTeamResultDto decepticonsTeamResult = new TransformerWarTeamResultDto(
				TransformerType.D.getLabel(), survivingDecepticons);

		return buildWarResultDto(battleCount, Arrays.asList(autobotsTeamResult, decepticonsTeamResult), winningTeam);
	}

	TransformerWarResultDto buildWarResultDto(int battleCount, List<TransformerWarTeamResultDto> teamResults,
			String winningTeam) {
		TransformerWarResultDto warResult = new TransformerWarResultDto();
		warResult.setWinningTeam(winningTeam);
		warResult.setNumberOfBattles(battleCount);
		warResult.setTeams(teamResults);
		return warResult;
	}

	String determineWinningTeam(int autobotWinCount, int decepticonWinCount) {
		String winningTeam;

		if (autobotWinCount < decepticonWinCount) {
			winningTeam = TransformerType.D.getLabel();
		} else if (decepticonWinCount < autobotWinCount) {
			winningTeam = TransformerType.A.getLabel();
		} else {
			winningTeam = "Draw";
		}
		return winningTeam;
	}

	int computePotentialBattleCount(int autobotsSize, int decepticonsSize) {
		return autobotsSize <= decepticonsSize ? autobotsSize : decepticonsSize;
	}

	void divideIntoTeams(List<Transformer> transformerArmy, List<Transformer> autobots, List<Transformer> decepticons) {
		for (Transformer soldier : transformerArmy) {
			switch (soldier.getTransformerType()) {
			case A -> autobots.add(soldier);
			case D -> decepticons.add(soldier);
			}
		}
	}

	Transformer determineVictor(Transformer autobot, Transformer decepticon) {

		// Checks if there is a chance for one of the opponents to run away
		if (willOpponentRunAway(autobot, decepticon, WIN_THRESHOLD_COURAGE, WIN_THRESHOLD_STRENGTH)) {
			if (autobot.getCourage() < decepticon.getCourage()) {
				return decepticon;
			} else {
				return autobot;
			}

		}

		// Checks if there is a mismatch of skills between the two opponents
		if (areSkillsMismatched(autobot, decepticon, WIN_THRESHOLD_SKILLS)) {
			if (autobot.getSkill() < decepticon.getSkill()) {
				return decepticon;
			} else {
				return autobot;
			}
		}

		return determineVictorByRating(autobot, decepticon);
	}

	Transformer determineVictorByRating(Transformer autobot, Transformer decepticon) {
		if (autobot.getOverallRating() < decepticon.getOverallRating()) {
			return decepticon;
		} else if (decepticon.getOverallRating() < autobot.getOverallRating()) {
			return autobot;
		} else {
			// If the overall ratings are the same, the battle is drawn and there is no
			// victor
			return null;
		}
	}

	/**
	 * Determines if there is a significant mismatch of skills between two
	 * transformers.
	 * 
	 * @param autobot            {@link Transformer} from the Autobots team
	 * @param decepticon         {@link Transformer} from the Decepticon team
	 * @param skillsAdvThreshold The minimum difference in skills to provide an
	 *                           advantage
	 * @return true if there is a significant mismatch, false if not
	 */
	boolean areSkillsMismatched(Transformer autobot, Transformer decepticon, int skillsAdvThreshold) {
		return exceedDifferenceThreshold(autobot.getSkill(), decepticon.getSkill(), skillsAdvThreshold);
	}

	/**
	 * Determines the possibility of an opponent running away based on the
	 * configuration of two transformers
	 * 
	 * @param autobot              {@link Transformer} from the Autobots team
	 * @param decepticon           {@link Transformer} from the Decepticon team
	 * @param courageAdvThreshold  The minimum difference in courage to provide an
	 *                             advantage
	 * @param strengthAdvThreshold The minimum difference in strength to provide an
	 *                             advantage
	 * @return true if the difference is larger than the thresholds specified, false
	 *         if not
	 */
	boolean willOpponentRunAway(Transformer autobot, Transformer decepticon, int courageAdvThreshold,
			int strengthAdvThreshold) {
		int decepticonCourage = decepticon.getCourage();
		int decepticonStrength = decepticon.getStrength();

		int autobotCourage = autobot.getCourage();
		int autobotStrength = autobot.getStrength();

		return willOpponentRunAway(courageAdvThreshold, strengthAdvThreshold, decepticonCourage, decepticonStrength,
				autobotCourage, autobotStrength);
	}

	boolean willOpponentRunAway(int courageAdvThreshold, int strengthAdvThreshold, int decepticonCourage,
			int decepticonStrength, int autobotCourage, int autobotStrength) {
		if (hasCourageAndStrengthAdvantage(decepticonCourage, decepticonStrength, autobotCourage, autobotStrength)) {
			if (bothValuesExceedThreshold(courageAdvThreshold, strengthAdvThreshold, decepticonCourage,
					decepticonStrength, autobotCourage, autobotStrength)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if the difference in both the courage and strength values of two
	 * Transformers cross the thresholds specified
	 * 
	 * @param courageAdvThreshold  Difference threshold of courage values
	 * @param strengthAdvThreshold Difference threshold of strength values
	 * @param transformer1Courage  Courage of the first transformer
	 * @param transformer1Strength Strength of the first transformer
	 * @param transformer2Courage  Courage of the second transformer
	 * @param transformer2Strength Strength of the first transformer
	 * 
	 * @return true the difference in both values exceed the thresholds provided
	 */
	boolean bothValuesExceedThreshold(int courageAdvThreshold, int strengthAdvThreshold, int transformer1Courage,
			int transfomer1Strength, int transformer2Courage, int transfomer2Strength) {
		return exceedDifferenceThreshold(transformer2Courage, transformer1Courage, courageAdvThreshold)
				&& exceedDifferenceThreshold(transfomer2Strength, transfomer1Strength, strengthAdvThreshold);
	}

	/**
	 * Computes if a particular Transformer has both a strength and courage
	 * advantage over another Transformer based on the values provided
	 * 
	 * @param transformer1Courage  Courage of the first transformer
	 * @param transformer1Strength Strength of the first transformer
	 * @param transformer2Courage  Courage of the second transformer
	 * @param transformer2Strength Strength of the first transformer
	 * 
	 * @return true if either of the transformers has greater strength and courage,
	 *         false if not
	 */
	boolean hasCourageAndStrengthAdvantage(int transformer1Courage, int transformer1Strength, int transformer2Courage,
			int transformer2Strength) {
		return (transformer2Courage < transformer1Courage && transformer2Strength < transformer1Strength)
				|| (transformer1Courage < transformer2Courage && transformer1Strength < transformer2Strength);
	}

	/**
	 * Determines if the absolute difference between two values exceeds a prescribed
	 * threshold
	 */
	boolean exceedDifferenceThreshold(int autobotValue, int decepticonValue, int differenceThreshold) {
		return Math.abs(autobotValue - decepticonValue) >= differenceThreshold;
	}

}
