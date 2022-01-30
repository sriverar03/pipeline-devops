/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
  
  pipeline {

	agent any
	 
	

	parameters {
  		choice choices: ['gradle', 'maven'], description: 'indicar herramienta de construccion', name: 'builTools'
		string(
			defaultValue: 'prueba',
			description: 'Enables debug information in the log',
			name: 'prueba')
		
	}

	stages{
		stage('Pipeline'){
			steps{
				script{				    
					println "Pipeline"

					if(params.builTools == 'gradle')
					{
					   gradle(prueba)
					}
					else
					{
					   maven()
					}
				}
			}
		}		
		
	}
}

}

return this;
