package com.vogella.gradle.plugin

import groovy.lang.MetaClass

class CreateHtmlOutput extends AsciiDoc {

	public CreateHtmlOutput() {
		description = 'Creates Html 5 Documentation'
		group = 'Documentation'
		attributes	'sectlinks':'true',
                'toc-title':''
		
		
		backends = ['html5']

		resources {
			from(sourceDir) { include 'img/**' }
		}
	}

   def matchesFilePattern(fileName) {
		fileName.startsWith("001_article") || fileName.contains('exercise_') 
   }
}
