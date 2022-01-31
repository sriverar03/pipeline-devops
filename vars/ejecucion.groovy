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
		string(defaultValue: '', name: 'stage', trim: true)
		
		
	}

	stages{
		stage('Pipeline'){
			steps{
				script{				    
					println "Pipeline"
					
					

					if(params.builTools == 'gradle')
					{
					   gradle(BranchName())
					}
					else
					{
					   maven(BranchName())
					}
				}
			}
		}		
		
	}
}

}

def BranchName(){
	if(env.GIT_BRANCH.contains('feature-') || env.GIT_BRANCH.contains('develop'))
	{ return 'CI'}
	else
	{ return 'CD}
	
}

return this;
