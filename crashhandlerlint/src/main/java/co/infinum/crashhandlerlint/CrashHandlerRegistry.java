package co.infinum.crashhandlerlint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

import co.infinum.crashhandlerlint.rules.LogUsageDetector;

/**
 * Created by Željko Plesac on 04/01/16.
 */
public class CrashHandlerRegistry extends IssueRegistry {

    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(LogUsageDetector.ISSUE);
    }
}
