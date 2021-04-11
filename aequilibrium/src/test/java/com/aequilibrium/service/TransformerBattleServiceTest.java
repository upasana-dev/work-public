package com.aequilibrium.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.aequilibrium.dto.TransformerWarResultDto;
import com.aequilibrium.model.Transformer;
import com.aequilibrium.model.constants.TransformerType;

public class TransformerBattleServiceTest {

	private TransformerBattleService service = new TransformerBattleService();

	@Test
	public void testExceedDifferenceThreshold_differentValues_lesserThanThreshold_value1Lesser() {
		int value1 = 3;
		int value2 = 4;

		int threshold = 2;

		assertFalse(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testExceedDifferenceThreshold_differentValues_lesserThanThreshold_value2Lesser() {
		int value1 = 10;
		int value2 = 11;

		int threshold = 2;

		assertFalse(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testExceedDifferenceThreshold_sameValues_lesserThanThreshold() {
		int value1 = 10;
		int value2 = 10;

		int threshold = 2;

		assertFalse(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testExceedDifferenceThreshold_differentValues_equalToThreshold_value2Lesser() {
		int value1 = 10;
		int value2 = 8;

		int threshold = 2;

		assertTrue(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testExceedDifferenceThreshold_differentValues_equalToThreshold_value1Lesser() {
		int value1 = 10;
		int value2 = 14;

		int threshold = 4;

		assertTrue(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testExceedDifferenceThreshold_differentValues_exceedThreshold_value1Lesser() {
		int value1 = 8;
		int value2 = 16;

		int threshold = 5;

		assertTrue(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testExceedDifferenceThreshold_sameValues_equalThreshold_value1Lesser() {
		int value1 = 16;
		int value2 = 16;

		int threshold = 0;

		assertTrue(service.exceedDifferenceThreshold(value1, value2, threshold));
	}

	@Test
	public void testAreSkillsMismatched_populatedValues() {
		Transformer autobot = new Transformer();
		autobot.setSkill(5);
		autobot.setCourage(6);

		Transformer decepticon = new Transformer();
		decepticon.setSkill(8);
		decepticon.setCourage(5);

		assertTrue(service.areSkillsMismatched(autobot, decepticon, 3));
	}

	@Test
	public void testAreSkillsMismatched_unpopulatedValues() {
		Transformer autobot = new Transformer();

		Transformer decepticon = new Transformer();

		assertFalse(service.areSkillsMismatched(autobot, decepticon, 3));
	}

	@Test
	public void testWillOpponentRunAway_bothStrengthAndCourageAdvantage_bothLesserThanThresholds() {

		int courage1 = 4;
		int strength1 = 5;

		// Transformer 2 has more courage and strength
		int courage2 = 6;
		int strength2 = 8;

		int courageThreshold = 3;
		int strengthThreshold = 4;

		// One of the players will not run away because the difference doesn't exceed
		// the thresholds specified
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_bothStrengthAndCourageAdvantage_oneLesserThanThresholds_strength() {

		int courage1 = 4;
		int strength1 = 6;

		// Transformer 2 has more courage and strength

		// The difference in courage exceeds the threshold
		int courage2 = 10;
		int strength2 = 8;

		int courageThreshold = 3;
		int strengthThreshold = 4;

		// Neither player will forfeit because the difference in only the
		// courage (and not the strength) exceeds the thresholds specified
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_bothStrengthAndCourageAdvantage_oneLesserThanThresholds_courage() {

		int courage1 = 4;
		int strength1 = 6;

		// Transformer 2 has more courage and strength

		// The difference in strength exceeds the threshold
		int courage2 = 5;
		int strength2 = 10;

		int courageThreshold = 3;
		int strengthThreshold = 2;

		// Neither player will forfeit because the difference in only the
		// strength (and not the courage) exceeds the thresholds specified
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_bothStrengthAndCourageAdvantage_bothGreaterThanThresholds() {

		int courage1 = 4;
		int strength1 = 6;

		// Transformer 2 has more courage and strength

		// The difference in both courage and strength exceeds the threshold
		int courage2 = 10;
		int strength2 = 11;

		int courageThreshold = 3;
		int strengthThreshold = 4;

		// One of the players will forfeit because the difference in strength and
		// courage in the stronger player exceeds the thresholds specified
		assertTrue(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_splitAdvantage_bothGreaterThanThresholds1() {

		// Transformer 1 has the courage advantage
		int courage1 = 9;
		int strength1 = 6;

		// Transformer 2 has the strength advantage

		int courage2 = 3;
		int strength2 = 11;

		// The difference in both courage and strength exceeds the threshold
		int courageThreshold = 5;
		int strengthThreshold = 4;

		// Neither player will automatically forfeit because although the
		// differences in both courage and strength exceed the thresholds specified, the
		// advantage is split between the two players
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_splitAdvantage_bothGreaterThanThresholds2() {

		// Transformer 1 has the strength advantage
		int courage1 = 1;
		int strength1 = 7;

		// Transformer 2 has the courage advantage

		int courage2 = 8;
		int strength2 = 2;

		// The difference in both courage and strength meetss the threshold
		int courageThreshold = 5;
		int strengthThreshold = 5;

		// One of the players will not automatically forfeit because although the
		// differences in both courage and strength exceed the thresholds specified, the
		// advantage is split between the two players
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_splitAdvantage_oneLesserThanThreshold() {

		// Transformer 1 has the strength advantage
		int courage1 = 1;
		int strength1 = 7;

		// Transformer 2 has the courage advantage

		int courage2 = 5;
		int strength2 = 2;

		// The difference in both courage and strength meets the threshold
		int courageThreshold = 5;
		int strengthThreshold = 5;

		// One of the players will not automatically forfeit because although the
		// differences in both courage and strength exceed the thresholds specified, the
		// advantage is split between the two players
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_equalStrength_bothMeetThresholds() {

		int courage1 = 4;
		int strength1 = 6;

		int courage2 = 10;
		int strength2 = 6;

		int courageThreshold = 3;
		int strengthThreshold = 0;

		// One of the players will not automatically forfeit because there is no clear
		// strength advantage
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_equalStrengthAndCourage_bothMeetThresholds() {

		int courage1 = 4;
		int strength1 = 6;

		// Transformer 2 has more courage and strength

		// The difference in both courage and strength exceeds the threshold
		int courage2 = 4;
		int strength2 = 6;

		int courageThreshold = 0;
		int strengthThreshold = 0;

		// One of the players will not automatically forfeit because there is no clear
		// strength advantage
		assertFalse(service.willOpponentRunAway(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testWillOpponentRunAway_noValues() {

		Transformer transformer1 = new Transformer();
		Transformer transformer2 = new Transformer();

		int courageThreshold = 2;
		int strengthThreshold = 6;

		assertFalse(service.willOpponentRunAway(transformer1, transformer2, courageThreshold, strengthThreshold));
	}

	@Test
	public void testHasCourageAndStrengthAdvantage_equalQualities() {
		int courage1 = 1;
		int strength1 = 2;

		int courage2 = 1;
		int strength2 = 2;

		assertFalse(service.hasCourageAndStrengthAdvantage(courage1, strength1, courage2, strength2));

	}

	@Test
	public void testHasCourageAndStrengthAdvantage_equalCourage_differentStrengths() {
		int courage1 = 1;
		int strength1 = 2;

		int courage2 = 1;
		int strength2 = 8;

		assertFalse(service.hasCourageAndStrengthAdvantage(courage1, strength1, courage2, strength2));

	}

	@Test
	public void testHasCourageAndStrengthAdvantage_equalStrength_differingCourage() {
		int courage1 = 3;
		int strength1 = 8;

		int courage2 = 7;
		int strength2 = 8;

		assertFalse(service.hasCourageAndStrengthAdvantage(courage1, strength1, courage2, strength2));

	}

	@Test
	public void testHasCourageAndStrengthAdvantage_differingQualities_splitAdvantage() {
		int courage1 = 3;
		int strength1 = 8;

		int courage2 = 7;
		int strength2 = 4;

		assertFalse(service.hasCourageAndStrengthAdvantage(courage1, strength1, courage2, strength2));

	}

	@Test
	public void testHasCourageAndStrengthAdvantage_differingQualities_combinedAdvantage_transformer1() {
		int courage1 = 7;
		int strength1 = 8;

		int courage2 = 3;
		int strength2 = 4;

		assertTrue(service.hasCourageAndStrengthAdvantage(courage1, strength1, courage2, strength2));

	}

	@Test
	public void testHasCourageAndStrengthAdvantage_differingQualities_combinedAdvantage_transformer2() {
		int courage1 = 2;
		int strength1 = 5;

		int courage2 = 6;
		int strength2 = 9;

		assertTrue(service.hasCourageAndStrengthAdvantage(courage1, strength1, courage2, strength2));
	}

	@Test
	public void testBothValuesExceedThresholds_neitherExceeds() {
		int courage1 = 4;
		int strength1 = 6;

		int courage2 = 7;
		int strength2 = 3;

		int courageThreshold = 5;
		int strengthThreshold = 4;

		assertFalse(service.bothValuesExceedThreshold(courageThreshold, strengthThreshold, courage1, strength1,
				courage2, strength2));
	}

	@Test
	public void testBothValuesExceedThresholds_strengthExceeds() {
		int courage1 = 4;
		int strength1 = 6;

		int courage2 = 7;
		int strength2 = 3;

		int courageThreshold = 5;

		// The strength threshold is crossed
		int strengthThreshold = 3;

		assertFalse(service.bothValuesExceedThreshold(courageThreshold, strengthThreshold, courage1, strength1,
				courage2, strength2));
	}

	@Test
	public void testBothValuesExceedThresholds_courageExceeds() {
		int courage1 = 4;
		int strength1 = 6;

		int courage2 = 7;
		int strength2 = 3;

		// The courage threshold is crossed
		int courageThreshold = 2;
		int strengthThreshold = 5;

		assertFalse(service.bothValuesExceedThreshold(courageThreshold, strengthThreshold, courage1, strength1,
				courage2, strength2));
	}

	@Test
	public void testBothValuesExceedThresholds_bothExceed() {
		int courage1 = 4;
		int strength1 = 6;

		int courage2 = 7;
		int strength2 = 3;

		int courageThreshold = 2;
		int strengthThreshold = 2;

		assertTrue(service.bothValuesExceedThreshold(courageThreshold, strengthThreshold, courage1, strength1, courage2,
				strength2));
	}

	@Test
	public void testDetermineVictorByRating_firstTransformerWithHigherRating() {
		Transformer transformer1 = new Transformer();
		transformer1.setOverallRating(45);

		Transformer transformer2 = new Transformer();
		transformer2.setOverallRating(40);

		assertEquals(transformer1, service.determineVictorByRating(transformer1, transformer2));
	}

	@Test
	public void testDetermineVictorByRating_firstTransformerWithLowerRating() {
		Transformer transformer1 = new Transformer();
		transformer1.setOverallRating(45);

		Transformer transformer2 = new Transformer();
		transformer2.setOverallRating(56);

		assertEquals(transformer2, service.determineVictorByRating(transformer1, transformer2));
	}

	@Test
	public void testDetermineVictorByRating_equalRatings() {
		Transformer transformer1 = new Transformer();
		transformer1.setOverallRating(56);

		Transformer transformer2 = new Transformer();
		transformer2.setOverallRating(56);

		// Since the ratings are equal, there is no victor
		assertNull(service.determineVictorByRating(transformer1, transformer2));
	}

	@Test
	public void testDivideIntoTeams_withData() {
		List<Transformer> transformers = new ArrayList<>();

		Transformer t1 = new Transformer();
		t1.setTransformerType(TransformerType.A);

		Transformer t2 = new Transformer();
		t2.setTransformerType(TransformerType.D);

		Transformer t3 = new Transformer();
		t3.setTransformerType(TransformerType.D);

		Transformer t4 = new Transformer();
		t4.setTransformerType(TransformerType.A);

		Transformer t5 = new Transformer();
		t5.setTransformerType(TransformerType.D);

		transformers.add(t1);
		transformers.add(t2);
		transformers.add(t3);
		transformers.add(t4);
		transformers.add(t5);

		List<Transformer> autobots = new ArrayList<>();
		List<Transformer> decepticons = new ArrayList<>();

		service.divideIntoTeams(transformers, autobots, decepticons);

		assertEquals(2, autobots.size());
		assertTrue(autobots.contains(t1));
		assertTrue(autobots.contains(t4));

		assertEquals(3, decepticons.size());
		assertTrue(decepticons.contains(t2));
		assertTrue(decepticons.contains(t3));
		assertTrue(decepticons.contains(t5));
	}

	@Test
	public void testDivideIntoTeams_noTransformers() {
		List<Transformer> transformers = new ArrayList<>();

		List<Transformer> autobots = new ArrayList<>();
		List<Transformer> decepticons = new ArrayList<>();

		service.divideIntoTeams(transformers, autobots, decepticons);

		assertTrue(autobots.isEmpty());
		assertTrue(decepticons.isEmpty());
	}

	@Test
	public void testComputePotentialBattleCount_moreDecepticons() {
		int autobotsSize = 5;
		int decepticonsSize = 7;

		assertEquals(5, service.computePotentialBattleCount(autobotsSize, decepticonsSize));
	}

	@Test
	public void testComputePotentialBattleCount_equalNumbers() {
		int autobotsSize = 7;
		int decepticonsSize = 7;

		assertEquals(7, service.computePotentialBattleCount(autobotsSize, decepticonsSize));
	}

	@Test
	public void testComputePotentialBattleCount_moreAutobots() {
		int autobotsSize = 7;
		int decepticonsSize = 4;

		assertEquals(4, service.computePotentialBattleCount(autobotsSize, decepticonsSize));
	}

	@Test
	public void testComputePotentialBattleCount_noTransformers() {
		int autobotsSize = 0;
		int decepticonsSize = 0;

		assertEquals(0, service.computePotentialBattleCount(autobotsSize, decepticonsSize));
	}

	@Test
	public void testDetermineWinningTeam_higherAutobotWinCount() {
		int autobotWinCount = 6;
		int decepticonWinCount = 3;

		assertEquals("Autobots", service.determineWinningTeam(autobotWinCount, decepticonWinCount));
	}

	@Test
	public void testDetermineWinningTeam_higherDecepticonWinCount() {
		int autobotWinCount = 2;
		int decepticonWinCount = 3;

		assertEquals("Decepticons", service.determineWinningTeam(autobotWinCount, decepticonWinCount));
	}

	@Test
	public void testDetermineWinningTeam_equalWins() {
		int autobotWinCount = 6;
		int decepticonWinCount = 6;

		assertEquals("Draw", service.determineWinningTeam(autobotWinCount, decepticonWinCount));
	}

	@Test
	public void testDetermineVictor_allPowerfulAutobot() {
		Transformer autobot = new Transformer();
		Transformer decepticon = new Transformer();

		assertEquals(autobot, service.determineVictor(autobot, decepticon, true, false));
	}

	@Test
	public void testDetermineVictor_allPowerfulDecepticon() {
		Transformer autobot = new Transformer();
		Transformer decepticon = new Transformer();

		assertEquals(decepticon, service.determineVictor(autobot, decepticon, false, true));
	}

	@Test
	public void testDetermineVictor_regularTransformers() {
		Transformer autobot = new Transformer();
		autobot.setOverallRating(34);

		Transformer decepticon = new Transformer();
		decepticon.setOverallRating(36);

		assertEquals(decepticon, service.determineVictor(autobot, decepticon, false, false));
	}

	@Test
	public void testDetermineVictor_runawayOpponent() {

		Transformer autobot = new Transformer();
		autobot.setStrength(7);
		autobot.setCourage(8);
		autobot.setSkill(5);
		autobot.setOverallRating(34);

		Transformer decepticon = new Transformer();
		decepticon.setStrength(4);
		decepticon.setCourage(4);
		decepticon.setSkill(9);
		decepticon.setOverallRating(36);

		// The autobot has a high combination of strength and courage, which results in
		// its opponent running away, despite the higher skill and overall rating of its
		// opponent
		assertEquals(autobot, service.determineVictor(autobot, decepticon));
	}

	@Test
	public void testDetermineVictor_outSkilledOpponent() {

		Transformer autobot = new Transformer();
		autobot.setStrength(6);
		autobot.setCourage(6);
		autobot.setSkill(5);
		autobot.setOverallRating(40);

		Transformer decepticon = new Transformer();
		decepticon.setStrength(7);
		decepticon.setCourage(8);
		decepticon.setSkill(9);
		decepticon.setOverallRating(36);

		// The decepticon is more skillful than the auto, allowing it to defeat its
		// opponent, despite the overall rating of its opponent
		assertEquals(decepticon, service.determineVictor(autobot, decepticon));
	}

	@Test
	public void testDetermineVictor_fairBattle() {

		Transformer autobot = new Transformer();
		autobot.setStrength(7);
		autobot.setCourage(9);
		autobot.setSkill(8);
		autobot.setOverallRating(38);

		Transformer decepticon = new Transformer();
		decepticon.setStrength(6);
		decepticon.setCourage(7);
		decepticon.setSkill(8);
		decepticon.setOverallRating(42);

		// In the absence of any big advantage of one over the other, the decepticon
		// wins due its higher overall rating
		assertEquals(decepticon, service.determineVictor(autobot, decepticon));
	}

	@Test
	public void testDetermineVictor_equallyMatched() {

		Transformer autobot = new Transformer();
		autobot.setStrength(7);
		autobot.setCourage(9);
		autobot.setSkill(8);
		autobot.setOverallRating(40);

		Transformer decepticon = new Transformer();
		decepticon.setStrength(6);
		decepticon.setCourage(7);
		decepticon.setSkill(8);
		decepticon.setOverallRating(40);

		// Since both Transformers are equally matched due to their equal rating, there
		// is a no victor
		assertNull(service.determineVictor(autobot, decepticon));
	}

	@Test
	public void testConstructWarResult() {
		int battleCount = 6;
		String winningTeam = "Dummy Winner";

		List<String> autobotSurvivors = Arrays.asList("Auto 1", "Auto 2");
		List<String> decepticonSurvivors = Arrays.asList("Dec 1", "Dec 2", "Dec 3");

		TransformerWarResultDto warResult = service.constructWarResult(battleCount, autobotSurvivors,
				decepticonSurvivors, winningTeam);

		assertNotNull(warResult);

		assertEquals(winningTeam, warResult.getWinningTeam());
		assertEquals(battleCount, warResult.getNumberOfBattles());

		assertEquals(2, warResult.getTeams().size());

		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertEquals(2, warResult.getTeams().get(0).getSurvivors().size());

		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(3, warResult.getTeams().get(1).getSurvivors().size());
	}

	@Test
	public void testExtractExcludedSurvivors_manyExcluded() {

		Transformer t1 = new Transformer();
		t1.setName("T1");

		Transformer t2 = new Transformer();
		t2.setName("T2");

		Transformer t3 = new Transformer();
		t3.setName("T3");

		List<Transformer> transformers = Arrays.asList(t1, t2, t3);

		List<String> excludedSurvivors = service.extractExcludedSurvivors(transformers, 1);

		assertNotNull(excludedSurvivors);
		assertEquals(2, excludedSurvivors.size());
		assertTrue(excludedSurvivors.contains(t2.getName()));
		assertTrue(excludedSurvivors.contains(t3.getName()));
	}

	@Test
	public void testExtractExcludedSurvivors_oneExcluded() {

		Transformer t1 = new Transformer();
		t1.setName("T1");

		Transformer t2 = new Transformer();
		t2.setName("T2");

		List<Transformer> transformers = Arrays.asList(t1, t2);

		List<String> excludedSurvivors = service.extractExcludedSurvivors(transformers, 1);

		assertNotNull(excludedSurvivors);
		assertEquals(1, excludedSurvivors.size());
		assertTrue(excludedSurvivors.contains(t2.getName()));
	}

	@Test
	public void testExtractExcludedSurvivors_noExcluded() {

		Transformer t1 = new Transformer();
		t1.setName("T1");

		List<Transformer> transformers = Arrays.asList(t1);

		List<String> excludedSurvivors = service.extractExcludedSurvivors(transformers, 1);

		assertNotNull(excludedSurvivors);
		assertEquals(0, excludedSurvivors.size());
	}

	@Test
	public void testIsAllPowerful_true() {
		assertTrue(service.isAllPowerful("Optimus Prime"));
		assertTrue(service.isAllPowerful("Predaking"));
	}

	@Test
	public void testIsAllPowerful_false() {
		assertFalse(service.isAllPowerful("Dummy"));
	}

	@Test
	public void testComputeWarResult_noAllPowerfuls_winningTeam_oneDraw() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Auto 1");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(9);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);

		Transformer autoBot2 = new Transformer();
		autoBot2.setTransformerType(TransformerType.A);
		autoBot2.setName("Auto 2");
		autoBot2.setCourage(7);
		autoBot2.setEndurance(6);
		autoBot2.setFirepower(5);
		autoBot2.setIntelligence(3);
		autoBot2.setRank(7);
		autoBot2.setSkill(7);
		autoBot2.setSpeed(8);
		autoBot2.setStrength(6);
		autoBot2.setOverallRating(28);

		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);

		// Auto 2 and Dec 2 are identical, so it will be a draw between them
		Transformer decepticon2 = new Transformer();
		decepticon2.setTransformerType(TransformerType.D);
		decepticon2.setName("Dec 2");
		decepticon2.setCourage(7);
		decepticon2.setEndurance(6);
		decepticon2.setFirepower(5);
		decepticon2.setIntelligence(3);
		decepticon2.setRank(6);
		decepticon2.setSkill(7);
		decepticon2.setSpeed(8);
		decepticon2.setStrength(6);
		decepticon2.setOverallRating(28);

		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1, autoBot2),
				Arrays.asList(decepticon1, decepticon2));

		assertNotNull(warResult);
		assertEquals(2, warResult.getNumberOfBattles());
		assertEquals(TransformerType.D.getLabel(), warResult.getWinningTeam());

		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertTrue(warResult.getTeams().get(0).getSurvivors().isEmpty());

		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(1, warResult.getTeams().get(1).getSurvivors().size());
		assertTrue(warResult.getTeams().get(1).getSurvivors().contains(decepticon1.getName()));
	}
	
	@Test
	public void testComputeWarResult_noAllPowerfuls_winningTeam() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Auto 1");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(10);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);
		
		Transformer autoBot2 = new Transformer();
		autoBot2.setTransformerType(TransformerType.A);
		autoBot2.setName("Auto 2");
		autoBot2.setCourage(7);
		autoBot2.setEndurance(6);
		autoBot2.setFirepower(8);
		autoBot2.setIntelligence(4);
		autoBot2.setRank(7);
		autoBot2.setSkill(7);
		autoBot2.setSpeed(8);
		autoBot2.setStrength(6);
		autoBot2.setOverallRating(32);
		
		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);
		
		Transformer decepticon2 = new Transformer();
		decepticon2.setTransformerType(TransformerType.D);
		decepticon2.setName("Dec 2");
		decepticon2.setCourage(7);
		decepticon2.setEndurance(6);
		decepticon2.setFirepower(5);
		decepticon2.setIntelligence(3);
		decepticon2.setRank(6);
		decepticon2.setSkill(7);
		decepticon2.setSpeed(8);
		decepticon2.setStrength(6);
		decepticon2.setOverallRating(28);
		
		Transformer decepticon3 = new Transformer();
		decepticon3.setTransformerType(TransformerType.D);
		decepticon3.setName("Dec 3");
		decepticon3.setCourage(7);
		decepticon3.setEndurance(6);
		decepticon3.setFirepower(5);
		decepticon3.setIntelligence(3);
		decepticon3.setRank(6);
		decepticon3.setSkill(7);
		decepticon3.setSpeed(8);
		decepticon3.setStrength(6);
		decepticon3.setOverallRating(28);
		
		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1, autoBot2),
				Arrays.asList(decepticon1, decepticon2, decepticon3));
		
		assertNotNull(warResult);
		assertEquals(2, warResult.getNumberOfBattles());
		assertEquals(TransformerType.A.getLabel(), warResult.getWinningTeam());
		
		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertEquals(2, warResult.getTeams().get(0).getSurvivors().size());
		assertTrue(warResult.getTeams().get(0).getSurvivors().contains(autoBot1.getName()));
		assertTrue(warResult.getTeams().get(0).getSurvivors().contains(autoBot2.getName()));
		
		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(1, warResult.getTeams().get(1).getSurvivors().size());
		assertTrue(warResult.getTeams().get(1).getSurvivors().contains(decepticon3.getName()));
	}
	
	@Test
	public void testComputeWarResult_matchedAllPowerfuls() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Auto 1");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(10);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);
		
		Transformer autoBot2 = new Transformer();
		autoBot2.setTransformerType(TransformerType.A);
		autoBot2.setName("Optimus Prime");
		autoBot2.setCourage(7);
		autoBot2.setEndurance(6);
		autoBot2.setFirepower(8);
		autoBot2.setIntelligence(4);
		autoBot2.setRank(7);
		autoBot2.setSkill(7);
		autoBot2.setSpeed(8);
		autoBot2.setStrength(6);
		autoBot2.setOverallRating(32);
		
		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);
		
		Transformer decepticon2 = new Transformer();
		decepticon2.setTransformerType(TransformerType.D);
		decepticon2.setName("Predaking");
		decepticon2.setCourage(7);
		decepticon2.setEndurance(6);
		decepticon2.setFirepower(5);
		decepticon2.setIntelligence(3);
		decepticon2.setRank(6);
		decepticon2.setSkill(7);
		decepticon2.setSpeed(8);
		decepticon2.setStrength(6);
		decepticon2.setOverallRating(28);
		
		Transformer decepticon3 = new Transformer();
		decepticon3.setTransformerType(TransformerType.D);
		decepticon3.setName("Dec 3");
		decepticon3.setCourage(7);
		decepticon3.setEndurance(6);
		decepticon3.setFirepower(5);
		decepticon3.setIntelligence(3);
		decepticon3.setRank(6);
		decepticon3.setSkill(7);
		decepticon3.setSpeed(8);
		decepticon3.setStrength(6);
		decepticon3.setOverallRating(28);
		
		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1, autoBot2),
				Arrays.asList(decepticon1, decepticon2, decepticon3));
		
		assertNotNull(warResult);
		//Two battles because one battle happened before the all-powerfuls clashed
		assertEquals(2, warResult.getNumberOfBattles());
		assertEquals("War terminated", warResult.getWinningTeam());
		
		//No survivors on either team
		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertEquals(0, warResult.getTeams().get(0).getSurvivors().size());
		
		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(0, warResult.getTeams().get(1).getSurvivors().size());
	}
	
	@Test
	public void testComputeWarResult_unmatchedAllPowerfuls_draw() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Optimus Prime");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(9);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);

		Transformer autoBot2 = new Transformer();
		autoBot2.setTransformerType(TransformerType.A);
		autoBot2.setName("Auto 2");
		autoBot2.setCourage(7);
		autoBot2.setEndurance(6);
		autoBot2.setFirepower(5);
		autoBot2.setIntelligence(3);
		autoBot2.setRank(7);
		autoBot2.setSkill(7);
		autoBot2.setSpeed(8);
		autoBot2.setStrength(6);
		autoBot2.setOverallRating(28);

		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);

		Transformer decepticon2 = new Transformer();
		decepticon2.setTransformerType(TransformerType.D);
		decepticon2.setName("Predaking");
		decepticon2.setCourage(7);
		decepticon2.setEndurance(6);
		decepticon2.setFirepower(5);
		decepticon2.setIntelligence(3);
		decepticon2.setRank(6);
		decepticon2.setSkill(7);
		decepticon2.setSpeed(8);
		decepticon2.setStrength(6);
		decepticon2.setOverallRating(28);

		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1, autoBot2),
				Arrays.asList(decepticon1, decepticon2));

		assertNotNull(warResult);
		assertEquals(2, warResult.getNumberOfBattles());
		assertEquals("Draw", warResult.getWinningTeam());

		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertEquals(1, warResult.getTeams().get(0).getSurvivors().size());
		assertTrue(warResult.getTeams().get(0).getSurvivors().contains(autoBot1.getName()));

		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(1, warResult.getTeams().get(1).getSurvivors().size());
		assertTrue(warResult.getTeams().get(1).getSurvivors().contains(decepticon2.getName()));
	}

	@Test
	public void testComputeWarResult_noAllPowerfuls_singleBattle() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Auto 1");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(9);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);

		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);

		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1),
				Arrays.asList(decepticon1));

		assertNotNull(warResult);
		assertEquals(1, warResult.getNumberOfBattles());
		assertEquals(TransformerType.D.getLabel(), warResult.getWinningTeam());

		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertTrue(warResult.getTeams().get(0).getSurvivors().isEmpty());

		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(1, warResult.getTeams().get(1).getSurvivors().size());
		assertTrue(warResult.getTeams().get(1).getSurvivors().contains(decepticon1.getName()));
	}

	@Test
	public void testComputeWarResult_singleAllPowerfuls_singleBattle() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Optimus Prime");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(9);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);

		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);

		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1),
				Arrays.asList(decepticon1));

		assertNotNull(warResult);
		assertEquals(1, warResult.getNumberOfBattles());
		assertEquals(TransformerType.A.getLabel(), warResult.getWinningTeam());

		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertEquals(1, warResult.getTeams().get(0).getSurvivors().size());
		assertTrue(warResult.getTeams().get(0).getSurvivors().contains(autoBot1.getName()));

		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(0, warResult.getTeams().get(1).getSurvivors().size());
	}

	@Test
	public void testComputeWarResult_noAllPowerfuls_singleBattle_unevenTeams() {
		Transformer autoBot1 = new Transformer();
		autoBot1.setTransformerType(TransformerType.A);
		autoBot1.setName("Auto 1");
		autoBot1.setCourage(8);
		autoBot1.setEndurance(6);
		autoBot1.setFirepower(7);
		autoBot1.setIntelligence(5);
		autoBot1.setRank(10);
		autoBot1.setSkill(9);
		autoBot1.setSpeed(5);
		autoBot1.setStrength(7);
		autoBot1.setOverallRating(25);

		Transformer autoBot2 = new Transformer();
		autoBot2.setTransformerType(TransformerType.A);
		autoBot2.setName("Auto 2");
		autoBot2.setCourage(5);
		autoBot2.setEndurance(6);
		autoBot2.setFirepower(7);
		autoBot2.setIntelligence(5);
		autoBot2.setRank(8);
		autoBot2.setSkill(9);
		autoBot2.setSpeed(5);
		autoBot2.setStrength(7);
		autoBot2.setOverallRating(22);

		Transformer decepticon1 = new Transformer();
		decepticon1.setTransformerType(TransformerType.D);
		decepticon1.setName("Dec 1");
		decepticon1.setCourage(7);
		decepticon1.setEndurance(6);
		decepticon1.setFirepower(5);
		decepticon1.setIntelligence(3);
		decepticon1.setRank(9);
		decepticon1.setSkill(7);
		decepticon1.setSpeed(8);
		decepticon1.setStrength(9);
		decepticon1.setOverallRating(31);

		TransformerWarResultDto warResult = service.computeWarResult(Arrays.asList(autoBot1, autoBot2),
				Arrays.asList(decepticon1));

		assertNotNull(warResult);
		assertEquals(1, warResult.getNumberOfBattles());
		assertEquals(TransformerType.D.getLabel(), warResult.getWinningTeam());

		assertEquals(TransformerType.A.getLabel(), warResult.getTeams().get(0).getName());
		assertEquals(1, warResult.getTeams().get(0).getSurvivors().size());
		assertTrue(warResult.getTeams().get(0).getSurvivors().contains(autoBot2.getName()));

		assertEquals(TransformerType.D.getLabel(), warResult.getTeams().get(1).getName());
		assertEquals(1, warResult.getTeams().get(1).getSurvivors().size());
		assertTrue(warResult.getTeams().get(1).getSurvivors().contains(decepticon1.getName()));
	}
}
