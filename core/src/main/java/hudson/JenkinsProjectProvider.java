package hudson;

import hudson.model.AbstractProject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jenkins.model.Jenkins;

public class JenkinsProjectProvider implements ProjectProvider {

    @Override
    public Collection<AbstractProject> getTopLevelProjects() {
        List<AbstractProject> topLevelProjects = new ArrayList<>();
        Jenkins jenkins = Jenkins.getInstanceOrNull();
        if (jenkins != null) {
            for (AbstractProject<?, ?> project : jenkins.getAllItems(AbstractProject.class)) {
                if (project.getUpstreamProjects().isEmpty()) {
                    topLevelProjects.add(project);
                }
            }
        }
        return topLevelProjects;
    }
}

