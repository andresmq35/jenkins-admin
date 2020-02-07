package io.open.cloud.jenkins

import jenkins.model.Jenkins

class BuildPolicyEnforcer {

    static void enforcePolicy () {
        Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
            if (job.isBuildable() && job.supportsLogRotator() && job.getProperty(jenkins.model.BuildDiscarderProperty) == null) {
                println "Processing \"${job.fullDisplayName}\""
                if ("${job.fullDisplayName}".equals('GCP--to--AWS--Storage')) {
                    job.setBuildDiscarder(new hudson.tasks.LogRotator ( daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep))
                    println "${job.fullName} is updated"
                }
            }
        }
        return;
    }
}
