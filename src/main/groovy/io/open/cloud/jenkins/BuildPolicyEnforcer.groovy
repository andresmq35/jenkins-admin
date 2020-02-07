package io.open.cloud.jenkins

import jenkins.model.Jenkins

final def daysToKeep = 30
final def numToKeep = 10
final def artifactDaysToKeep = 5
final def artifactNumToKeep = 3

def enforcePolicy = {
    Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
        def name = job.fullDisplayName
        if (job.isBuildable() && job.supportsLogRotator() && job.getProperty(jenkins.model.BuildDiscarderProperty) == null) {

            def buildable = job.isBuildable()
            def logRotate = job.supportsLogRotator()
            def discard = job.getProperty(jenkins.model.BuildDiscarderProperty)

            println "--- Enforcing Policy --- "
            println "Full display name: ${name}"
            println "Buildable: ${buildable}"
            println "Logs Rotate: ${logRotate}"
            println "Discard: ${discard}"
            job.setBuildDiscarder(new hudson.tasks.LogRotator ( daysToKeep, numToKeep, artifactDaysToKeep, artifactNumToKeep))
            println "--- End Policy Enforcement"
        }
    }
}

def verifyPolicies = {
    Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->

    }
}
node('master') {
    stage('Print Policy') {
        println '--- Policy ---'
        println 'daysToKeep : 30'
        println 'numToKeep = 10'
        println 'artifactDaysToKeep = 5'
        println 'artifactNumToKeep = 3'
        println '--- End Policy ---'
    }
    stage('Enforce Policy') {
        enforcePolicy()
    }
}
