properties([
    parameters([
        string(description: 'Provide node ip', name: 'node', trim: true)
        ])
    ])


node {
    stage("Pull repo"){
        git url: 'https://github.com/element1313/ansible-Flaskex.git' 
    }
    
    stage("Install flaskex"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'prerequisites.yml'
    }   

    withEnv(['FLASKEX_REPO=https://github.com/element1313/flaskex.git', 'FLASKEX_BRANCH=master']) {
        stage("pull repo"){
            ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'pull_repo.yml'
        }
    }

    
    stage("Install python"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'install_python.yml'
    }
    stage("Start app"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'start_app.yml'
    }
}
