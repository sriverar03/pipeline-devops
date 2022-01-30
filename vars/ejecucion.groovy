/*
	forma de invocación de método call:
	def ejecucion = load 'script.groovy'
	ejecucion.call()
*/

def call(){
  
  pipeline {

	agent any
	environment {			
			productionServer = 'production-myproject.mycompany.com'
		    }
	

	parameters {
  		choice choices: ['gradle', 'maven'], description: 'indicar herramienta de construccion', name: 'builTools'
		
		
	}

	stages{
		stage('Pipeline'){
			steps{
				script{				    
					println "Pipeline"

					if(params.builTools == 'gradle')
					{
					   gradle(env.productionServer)
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
