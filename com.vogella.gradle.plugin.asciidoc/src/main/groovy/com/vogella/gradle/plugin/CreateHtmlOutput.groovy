package com.vogella.gradle.plugin

import groovy.lang.MetaClass

class CreateHtmlOutput extends AsciiDoc {

	public CreateHtmlOutput() {
		description = 'Creates Html 5 Documentation'
		group = 'Documentation'
		attributes	'toclevels':'1',
		'sectlinks':'true',
                'toc-title':''
		
		
		backends = ['html5']

		resources {
			from(sourceDir) { include 'img/**' }
		}
	}
}
