/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
  
  		stage('Compile Code') {            
            		bat "mvn clean compile -e"
        }
        
        stage('SonarQube analysis') {
            
                    def scannerHome = tool 'sonar-scanner';
                    withSonarQubeEnv('sonar-server') { 
                    bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.sources=src -Dsonar.java.binaries=build " 
                    }
           
        }
        
         stage('Test Code') {           
                    bat "mvn clean test -e" 
        }

         stage('Jar Code') {
           
                    bat "mvn clean package -e"            
        }

         stage('Run Jar') {           
                    bat "start /min mvn spring-boot:run &"           
        }
        
        /*stage('Test Application') {
            steps {
                script{
                    bat "start chrome http://localhost:8081/rest/mscovid/test?msg=testing" 
                }                                    
            }
        }*/
        
        stage('Upload Nexus') {            
                    bat "curl -v --user admin:123456 --upload-file C:/Users/nmt02/.jenkins/workspace/pipilene_sonar_feature-sonar/build/DevOpsUsach2020-0.0.1.jar http://7fb6-186-79-184-102.ngrok.io/repository/test-repo/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar "            
        }

}

return this;
