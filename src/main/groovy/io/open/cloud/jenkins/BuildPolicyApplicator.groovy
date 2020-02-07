package io.open.cloud.jenkins

import jenkins.model.Jenkins
// NOTES:
// dryRun: to only list the jobs which would be changed
// daysToKeep:  If not -1, history is only kept up to this day.
// numToKeep: If not -1, only this number of build logs are kept.
// artifactDaysToKeep: If not -1 nor null, artifacts are only kept up to this day.
// artifactNumToKeep: If not -1 nor null, only this number of builds have their artifacts kept.




class BuildPolicyApplicator {
    dryRun = 'false'
    daysToKeep = 30
    numToKeep = 10
    artifactDaysToKeep = 5
    artifactNumToKeep = 3
    Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
        if (job.isBuildable() && job.supportsLogRotator() && job.getProperty(jenkins.model.BuildDiscarderProperty) == null) {
            println "Processing \"${job.fullDisplayName}\""
            if ("${job.fullDisplayName}".equals('GCP--to--AWS--Storage')) {
                job.setBuildDiscarder(new hudson.tasks.LogRotator ( daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep))
                println "${job.fullName} is updated"
            }
            // if (!"true".equals(dryRun)) {
            //     // adding a property implicitly saves so no explicit one
            //     try {
            //         job.setBuildDiscarder(new hudson.tasks.LogRotator ( daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep))
            //         println "${job.fullName} is updated"
            //     } catch (Exception e) {
            //         // Some implementation like for example the hudson.matrix.MatrixConfiguration supports a LogRotator but not setting it
            //         println "[WARNING] Failed to update ${job.fullName} of type ${job.class} : ${e}"
            //     }
            // }
        }
    }
    return;
}
