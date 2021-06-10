node {
    stage("Pull repo"){
        git branch: 'solution',  url: 'https://github.com/element1313/terraform-job.git'
    }


    dir('sandbox/') {
        withCredentials([usernamePassword(credentialsId: 'jenkins_aws_key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: ' AWS_ACCESS_KEY_ID')]) {
            stage("Terraform init"){
                sh """
                   terraform init
                """
            }
            stage("Terraform apply"){
                sh """
                   terraform apply  -auto-approve
                """
            }
        }
    }
}