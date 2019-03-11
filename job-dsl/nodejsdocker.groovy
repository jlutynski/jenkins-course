job('NodeJS Docker example') {
    scm {
        {  
            node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('Dsl-User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
        git { 
            remote { 
                github('jlutynski/jenkins-course', 'ssh') 
                credentials('gituser') 
            } 
        } 
        
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('jlutynski/docker-nodejs-demo')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}