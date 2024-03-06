package hudson;

import hudson.model.AbstractProject;
import java.util.Collection;

public interface ProjectProvider {
    Collection<? extends AbstractProject> getTopLevelProjects();
}
