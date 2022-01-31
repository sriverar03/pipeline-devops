/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
	
	String[] str
	//String[] stages = ['build','sonar','run','nexus']
      	str = params.stage.split(';')
  	println str.size()
	println "feature cicd"
	def bandera = true
	for (int i = 0; i < str.size(); i++) {
		switch(str[0]) {
			case "build":		   
			case "sonar":
			case "run":
			case "nexus":
			case " ":
			    bandera = true
		  
			 default:
			    bandera = false
			    break
		}	
	}
	
	println bandera
	if(bandera){
		if(str.contains('build') || params.stage.isEmpty() )
		{	
			stage('TestBuild') {            
				bat "gradle Build"
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

		if(str.contains('run') || params.stage.isEmpty())
		{
			stage('Run') {           
				bat "gradle bootRun "           
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
