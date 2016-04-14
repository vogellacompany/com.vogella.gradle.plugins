package com.vogella.gradle.plugin

import groovy.lang.MetaClass

class CreateAllOutputFormats extends AsciiDoc {

	public CreateAllOutputFormats() {
		description = 'Creates Html 5, PDF and Epub 3 Documentation'
		group = 'Documentation'

		backends = ['html5', 'pdf', 'epub3']

		resources {
			from(sourceDir) { include 'img/**' }
		}
	}
}
