package com.vogella.gradle.plugin

import groovy.lang.MetaClass
import org.gradle.api.tasks.Copy

class PublishHtmlOutput extends Copy {

	public PublishHtmlOutput() {
		description = 'Publishes Html 5 outputs into these rootfolder'
		group = 'Documentation'

		dependsOn = ['createHtml']

		from "${project.buildDir}/html5"
		into "../build/${project.name}"
	}
}
