package hudson;

import hudson.model.AbstractProject;
import hudson.security.ACL;
import hudson.security.ACLContext;
import java.util.*;
import java.util.logging.Logger;

public class DependencyRunner2 implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(DependencyRunner2.class.getName());
    List<AbstractProject> polledProjects = new ArrayList<>();
    private final ProjectProvider projectProvider;
    private final DependencyRunner2.ProjectRunnable runnable;

    public DependencyRunner2(ProjectProvider projectProvider, DependencyRunner2.ProjectRunnable runnable) {
        this.projectProvider = projectProvider;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try (ACLContext ctx = ACL.as2(ACL.SYSTEM2)) {
            Collection<? extends AbstractProject> projects = projectProvider.getTopLevelProjects();
            populate(projects);
            for (AbstractProject p : polledProjects) {
                LOGGER.fine("running project in correct dependency order: " + p.getName());
                runnable.run(p);
            }
        }
    }

    private void populate(Collection<? extends AbstractProject> projectList) {
        for (AbstractProject<?, ?> p : projectList) {
            if (polledProjects.contains(p)) {
                // Project will be readded at the queue, so that we always use
                // the longest path
                LOGGER.fine("removing project " + p.getName() + " for re-add");
                polledProjects.remove(p);
            }

            LOGGER.fine("adding project " + p.getName());
            polledProjects.add(p);

            // Add all downstream dependencies
            populate(p.getDownstreamProjects());
        }
    }

    public interface ProjectRunnable {
        void run(AbstractProject p);
    }


}
