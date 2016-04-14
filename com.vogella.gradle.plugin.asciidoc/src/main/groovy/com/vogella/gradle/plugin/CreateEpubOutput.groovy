package com.vogella.gradle.plugin

import groovy.lang.MetaClass

class CreateEpubOutput extends AsciiDoc {

	public CreateEpubOutput() {
		description = 'Creates Epub 3 Documentation'
		group = 'Documentation'

		backends = ['epub3']
	}
}
