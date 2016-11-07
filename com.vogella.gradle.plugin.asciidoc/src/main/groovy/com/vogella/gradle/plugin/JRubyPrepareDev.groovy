package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction
import com.github.jrubygradle.JRubyPrepare

class JRubyPrepareDev extends JRubyPrepare {
	static final String INTERNAL_BUILDVERSION = 1
	static final String GEM_NAME = "asciidoctor-pdf-1.5.0.alpha.14.dev.${INTERNAL_BUILDVERSION}"
	static final String GEM_FILENAME = "${GEM_NAME}.gem"

    public JRubyPrepareDev() {
		outputDir "${project.buildDir}/jruby_prepare"
		copyDevGem(gemPath)
        dependencies project.configurations.gems
        dependencies project.file(gemPath)
    }
	
	def copyDevGem(gemPath) {
		new File(gemFoldername).mkdirs()
		InputStream src = getClass().getResourceAsStream(GEM_FILENAME)
		def destFile = new File(gemPath)
		if (!destFile.exists()) {
			def dest = destFile.newDataOutputStream()
			dest << src
			src.close()
			dest.close()
		}
	}
	
	def getGemFoldername() {
		"${outputDir}/cache"
	}
	
	def getGemPath() {
		"${gemFoldername}/${GEM_FILENAME}"
	}
}