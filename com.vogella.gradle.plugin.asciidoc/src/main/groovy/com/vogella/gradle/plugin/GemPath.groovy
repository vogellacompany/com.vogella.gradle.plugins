package com.vogella.gradle.plugin

import org.gradle.api.DefaultTask

class GemPath {
	
	static final String INTERNAL_BUILDVERSION = 1
	static final String GEM_NAME = "asciidoctor-pdf-1.5.0.alpha.14.dev.${INTERNAL_BUILDVERSION}"
	static final String GEM_FILENAME = "${GEM_NAME}.gem"
	private def project
	
	public GemPath(project) {
		this.project = project
	}
	
	def getGemFoldername() {
		"${project.buildDir.absolutePath}/cache"
	}
	
	def getGemPath() {
		"${gemFoldername}/${GEM_FILENAME}"
	}
}
