package com.vogella.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.internal.impldep.bsh.This
import org.gradle.internal.impldep.org.eclipse.jdt.internal.eval.CodeSnippetThisReference

class CleanExercises extends DefaultTask {
	
	Object solutionRegex = $/(?sm)(// -{12} START EDITING HERE -{22})(.*?)(\s+// -{12} STOP EDITING HERE  -{22})/$
	
	public CleanExercises() {
		description = 'Removes the solutions from all exercises, so you can start fresh.'
		
		doLast {
			def solutionsRemoved = 0
			project.sourceSets.test.allSource.files.each { File sourceFile ->
				solutionsRemoved += (sourceFile.text =~ solutionRegex).count
				sourceFile.text = sourceFile.text.replaceAll(solutionRegex, '$1\n\n$3')
			}
			println "Removed $solutionsRemoved solutions from the exercises. Good luck!"
		}
	}

}
