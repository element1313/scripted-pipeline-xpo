properties([
    parameters([
        string(description: 'Provide node ip', name: 'node', trim: true)
        ])
    ])



node {
    stage("Pull repo"){
        sh "rm -rf ansible-melodi && git clone https://github.com/element1313/ansible-melodi.git" 
    }
    
    stage("Install Melodi"){
        ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master', disableHostKeyChecking: true, inventory: "${params.node},", playbook: 'ansible-melodi/main.yml'
    }   
}