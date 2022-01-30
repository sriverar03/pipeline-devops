/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
	
	String[] str;
      	str = params.stage.split(';');
  	println str[1]
	
	
	
	
	build()
	sonar()
	run()
	nexus()
	
        
        
        
        

        
        
        /*stage('Test Application') {           
                    bat "curl -X GET http://localhost:8081/rest/mscovid/test?msg=testing"             
        }*/
        
        

}

def build(){
	stage('TestBuild') {            
		bat "gradle Build"
	}
}

def (sonar){
	stage('SonarQube analysis') {            
                    def scannerHome = tool 'sonar-scanner';
                    withSonarQubeEnv('sonar-server') { 
                    bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.sources=src -Dsonar.java.binaries=build " 
                    }           
        }
}

def run(){
	stage('Run') {           
                bat "gradle bootRun "           
        }
}

def nexus(){
	stage('Nexus') {            
                bat "curl -v --user admin:123456 --upload-file C:/Users/nmt02/.jenkins/workspace/pipilene_sonar_feature-sonar/build/DevOpsUsach2020-0.0.1.jar http://7fb6-186-79-184-102.ngrok.io/repository/test-repo/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar "            
        }
}

return this;
