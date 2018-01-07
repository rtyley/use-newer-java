package use.newer.java;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static use.newer.java.Util.majorJavaVersionFrom;

public class UtilTest {

    /*
    see also
    https://bugs.openjdk.java.net/browse/JDK-8149519?focusedCommentId=13923931&page=com.atlassian.jira.plugin.system.issuetabpanels:comment-tabpanel#comment-13923931
    http://openjdk.java.net/jeps/223
     */
    @Test
    public void testShouldCorrectlyInterpretThings() {
        assertEquals(4, majorJavaVersionFrom("1.4"));

        assertEquals(5, majorJavaVersionFrom("1.5"));

        assertEquals(8, majorJavaVersionFrom("1.8"));
        assertEquals(8, majorJavaVersionFrom("1.8.0"));
        assertEquals(9, majorJavaVersionFrom("9.0.0"));
    }
}