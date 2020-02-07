package io.open.cloud.jenkins.release

def GenerateNotes = {
    // Capture job parameters into local variables
    def templateUrl = "https://github.com/andresmq35/jenkins-admin/blob/master/src/main/resources/templates/${TEMPLATE}.ejs"
    def templateFn = "${TEMPLATE}"
    def repo = "${REPOSITORY}"
    def agentNode = "${NODE}"
    def credentialsId = "${CREDENTIALS_ID}"
    def releaseBranch = "${RELEASE_BRANCH}"
    def from = "${FROM}"
    def to = "${TO}"
    def appName = "${APP_NAME}"
    def extension = 'html'
    if(templateFn.contains('markdown')) {
        extension = 'md'
    }
    def cmd = "git-release-notes ${from}..${to} ./${templateFn} > ${appName}-${to}.${extension}"
    println "CMD: ${cmd}"

    node(agentNode) {
        stage('Clean Workspace') {
            cleanWs()
        }
        stage('Node Installed?') {
            sh 'node -v'
        }
        stage('Checkout White Label') {
            git branch: releaseBranch, credentialsId: credentialsId, url: repo
        }
        stage('NPM Install Release Notes') {
            sh 'npm install -g git-release-notes'
        }
        stage('Download template to use') {
            httpRequest ignoreSslErrors: true, outputFile: templateFn, responseHandle: 'NONE', url: templateUrl
        }
        stage('Generate Notes') {
            sh cmd
        }
    }
}
