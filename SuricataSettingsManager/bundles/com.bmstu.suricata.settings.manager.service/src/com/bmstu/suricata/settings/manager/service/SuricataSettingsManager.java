package com.bmstu.suricata.settings.manager.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.bmstu.suricata.settings.manager.ISuricataPathFinder;
import com.bmstu.suricata.settings.manager.ISuricataSettingsManager;
import com.bmstu.suricata.settings.manager.model.Rule;
import com.bmstu.suricata.settings.manager.model.RuleSet;

/**
 *
 * Implementation of {@link ISuricataSettingsManager}.
 *
 * @author Nikolaev
 *
 */
@Component
public class SuricataSettingsManager implements ISuricataSettingsManager {

	private static final String PATH_TO_SURICATA_SETTINGS = "suricata.yaml";
	private static final String RULE_FILES = "rule-files";
	private static final String COMMENT_CHAR = "#";
	private static final String DASH_CHAR = "-";

	private ISuricataPathFinder suricataPathFinder;

	@Override
	public void addRuleSet(RuleSet ruleSet) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(getSuricataSettings()));
			int ruleSetsStartLineIndex = getRuleSetsStartIndex(lines);
			int ruleSetsEndLineIndex = getRuleSetsEndIndex(lines, ruleSetsStartLineIndex);

			int ruleSetLineIndex = getRuleSetLineIndex(ruleSet, lines, ruleSetsStartLineIndex, ruleSetsEndLineIndex);
			if (ruleSetLineIndex > ruleSetsStartLineIndex) {
				if (isCommented(ruleSet.getName(), lines.get(ruleSetLineIndex))) {
					lines = uncommentRuleSet(ruleSet, ruleSetLineIndex, lines);
				}
				else {
					return;
				}
			}
			else {
				lines = insertRuleSet(ruleSet, lines, ruleSetsEndLineIndex);
			}

			rewriteFile(getSuricataSettings(), lines);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeRuleSet(RuleSet ruleSet) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(getSuricataSettings()));
			int ruleSetsStartLineIndex = getRuleSetsStartIndex(lines);
			int ruleSetsEndLineIndex = getRuleSetsEndIndex(lines, ruleSetsStartLineIndex);

			int ruleSetLineIndex = getRuleSetLineIndex(ruleSet, lines, ruleSetsStartLineIndex, ruleSetsEndLineIndex);
			if (ruleSetLineIndex > ruleSetsStartLineIndex) {
				lines = removeLine(ruleSetLineIndex, lines);
				rewriteFile(getSuricataSettings(), lines);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRule(String path, Rule rule) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(getSuricataRoot() + path));
			int ruleLineIndex = getRuleLineIndex(lines, rule);

			if (ruleLineIndex >= 0) {
				if (isRuleCommented(lines.get(ruleLineIndex))) {
					lines = uncommentRuleLine(lines, ruleLineIndex);
				}
				else {
					return;
				}
			}
			else {
				lines = insertRule(lines, rule);
			}

			rewriteFile(getSuricataRoot() + path, lines);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeRule(String path, Rule rule) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(getSuricataRoot() + path));
			int ruleLineIndex = getRuleLineIndex(lines, rule);

			if (ruleLineIndex >= 0 && !isRuleCommented(lines.get(ruleLineIndex))) {
				lines = removeLine(ruleLineIndex, lines);
				rewriteFile(getSuricataRoot() + path, lines);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Reference(unbind = "-")
	public void bindSuricataPathFinder(ISuricataPathFinder suricataPathFinder)
	{
		this.suricataPathFinder = suricataPathFinder;
	}

	private String getSuricataRoot()
	{
		return suricataPathFinder.getSuricataPath();
	}

	private String getSuricataSettings()
	{
		return getSuricataRoot() + PATH_TO_SURICATA_SETTINGS;
	}

	private int getRuleSetsStartIndex(List<String> lines) {
		int lineIndex = 0;
		for (String line : lines) {
			if (line.startsWith(RULE_FILES)) {
				return lineIndex;
			}

			lineIndex++;
		}

		return 0;
	}

	private int getRuleSetsEndIndex(List<String> lines, int rulesStartLineIndex) {
		for (int lineIndex = rulesStartLineIndex; lineIndex < lines.size(); lineIndex++) {
			if (lines.get(lineIndex).isEmpty()) {
				return lineIndex;
			}
		}

		return 0;
	}

	private List<String> uncommentRuleSet(RuleSet rule, int ruleLineIndex, List<String> lines) {
		List<String> newLines = new ArrayList<>(lines.size());
		for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
			if (lineIndex == ruleLineIndex) {
				String line = lines.get(lineIndex);
				newLines.add(line.replaceFirst("^.+" + rule.getName(), " " + DASH_CHAR + " " + rule.getName()));
			}
			else {
				newLines.add(lines.get(lineIndex));
			}
		}

		return newLines;
	}

	private List<String> insertRuleSet(RuleSet rule, List<String> lines, int rulesEndLineIndex) {
		List<String> newLines = new ArrayList<>(lines.size() + 1);
		for (int lineIndex = 0; lineIndex < rulesEndLineIndex; lineIndex++) {
			newLines.add(lines.get(lineIndex));
		}
		newLines.add(createRuleLine(rule));
		for (int lineIndex = rulesEndLineIndex + 1; lineIndex < lines.size(); lineIndex++) {
			newLines.add(lines.get(lineIndex - 1));
		}

		return newLines;
	}

	private List<String> removeLine(int lineToRemoveIndex, List<String> lines) {
		List<String> newLines = new ArrayList<>(lines.size() - 1);
		for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
			if (lineIndex != lineToRemoveIndex) {
				newLines.add(lines.get(lineIndex));
			}
		}

		return newLines;
	}

	private String createRuleLine(RuleSet rule) {
		return " " + DASH_CHAR + " " + rule.getName() + " # auto added rule set";
	}

	private int getRuleSetLineIndex(RuleSet rule, List<String> lines, int rulesStartLineIndex, int rulesEndLineIndex) {
		for (int lineIndex = rulesStartLineIndex; lineIndex < rulesEndLineIndex; lineIndex++) {
			String lineRule = getLineRule(lines.get(lineIndex));
			if (lineRule.equals(rule.getName())) {
				return lineIndex;
			}
		}

		return -1;
	}

	private String getLineRule(String line) {
		String lineRule = line;
		while (lineRule.startsWith(" ") || lineRule.startsWith(COMMENT_CHAR) || lineRule.startsWith(DASH_CHAR)) {
			lineRule = lineRule.substring(1, lineRule.length());
		}

		int commentCharIndex = lineRule.indexOf(COMMENT_CHAR);
		if (commentCharIndex > 0) {
			lineRule = lineRule.substring(0, commentCharIndex);
			while (lineRule.endsWith(" ")) {
				lineRule = lineRule.substring(0, lineRule.length() - 1);
			}
		}

		return lineRule;
	}

	private void rewriteFile(String filePath, List<String> lines) {
		try {
			Files.write(Paths.get(filePath), lines);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isCommented(String ruleName, String line) {
		int indexOfRule = line.indexOf(ruleName);

		return line.substring(0, indexOfRule).contains(COMMENT_CHAR);
	}

	private int getRuleLineIndex(List<String> lines, Rule rule) {
		int lineIndex = 0;
		for (String line : lines) {
			while (line.startsWith(COMMENT_CHAR)) {
				line = line.substring(1);
			}

			if (!line.isEmpty() && containsRule(line, rule)) {
				return lineIndex;
			}

			lineIndex++;
		}

		return -1;
	}

	private boolean containsRule(String line, Rule rule) {
		return line.startsWith(rule.toString());
	}

	private boolean isRuleCommented(String ruleLine) {
		return ruleLine.startsWith(COMMENT_CHAR);
	}

	private List<String> uncommentRuleLine(List<String> lines, int ruleLineIndex) {
		List<String> newLines = new ArrayList<>(lines.size());
		for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
			if (lineIndex == ruleLineIndex) {
				String line = lines.get(lineIndex);
				while (line.startsWith(COMMENT_CHAR)) {
					line = line.substring(1);
				}

				newLines.add(line);
			}
			else {
				newLines.add(lines.get(lineIndex));
			}
		}

		return newLines;
	}

	private List<String> insertRule(List<String> lines, Rule rule) {
		List<String> newLines = new ArrayList<>(lines);
		newLines.add(rule.toString() + " # auto adder rule");

		return newLines;
	}
}
