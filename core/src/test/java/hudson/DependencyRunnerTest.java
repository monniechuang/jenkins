package hudson;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import hudson.model.AbstractProject;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;

public class DependencyRunnerTest {

    @Test
    public void testDependencyRunner() {
        ProjectProvider mockProvider = mock(ProjectProvider.class);
        DependencyRunner2.ProjectRunnable mockRunnable = mock(DependencyRunner2.ProjectRunnable.class);

        // Correctly typed list for Mockito
        Collection<AbstractProject> mockProjectList = new ArrayList<>();


        AbstractProject mockProject1 = mock(AbstractProject.class);
        mockProjectList.add(mockProject1);

        doReturn(mockProjectList).when(mockProvider).getTopLevelProjects();

        DependencyRunner2 runner = new DependencyRunner2(mockProvider, mockRunnable);
        runner.run();

        // Verify interactions
        verify(mockRunnable).run(mockProject1);
    }
}


