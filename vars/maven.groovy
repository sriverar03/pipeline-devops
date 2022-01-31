/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
	
	
	String[] str	
      	str = params.stage.split(';')
	
	def bandera = true
	for (int i = 0; i < str.size(); i++) {
		switch(str[0]) {
			case "compile":		   
			case "sonar":
			case "test":
			case "code":
			case "run":
			case "nexus":
			    bandera = true
		  
			 default:
			    bandera = false
			    break
		}	
	}
	
	
	
	if(bandera){
		if(str.contains('compile') || params.stage.isEmpty() )
		{	
			stage('Compile Code') {            
            			bat "mvn clean compile -e"
        		}
		}

		if(str.contains('sonar') || params.stage.isEmpty())
		{
			stage('SonarQube analysis') {            
				    def scannerHome = tool 'sonar-scanner';
				    withSonarQubeEnv('sonar-server') { 
				    bat "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.sources=src -Dsonar.java.binaries=build " 
			    }
           
        		}
		}

		if(str.contains('test') || params.stage.isEmpty())
		{
			stage('Test Code') {           
				bat "mvn clean test -e" 
			}
		}
		
		if(str.contains('code') || params.stage.isEmpty())
		{
			stage('Jar Code') {           
                    		bat "mvn clean package -e"            
        		}
		}
		
		if(str.contains('run') || params.stage.isEmpty())
		{
			 stage('Run Jar') {           
                    		bat "start /min mvn spring-boot:run &"           
        		}
		}

		if(str.contains('nexus') || params.stage.isEmpty())
		{
			stage('Nexus') {            
				bat "curl -v --user admin:123456 --upload-file C:/Users/nmt02/.jenkins/workspace/pipilene_sonar_feature-sonar/build/DevOpsUsach2020-0.0.1.jar http://7fb6-186-79-184-102.ngrok.io/repository/test-repo/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar "            
			} 
		}




		/*stage('Test Application') {           
				    bat "curl -X GET http://localhost:8081/rest/mscovid/test?msg=testing"             
		}*/
	}
	else{
		println 'verifique hay stages ingresados que no existen.'
	}

}

return this;
