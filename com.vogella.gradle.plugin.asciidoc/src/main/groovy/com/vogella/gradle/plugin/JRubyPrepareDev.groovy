package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction
import com.github.jrubygradle.JRubyPrepare

class JRubyPrepareDev extends JRubyPrepare {
	private def gemPath

    public JRubyPrepareDev() {
		gemPath = new GemPath(project)
		copyDevGem(gemPath)
        outputDir "${project.buildDir}"
        dependencies project.configurations.gems
        dependencies project.file(gemPath.gemPath)
    }
	
	def copyDevGem(gemPath) {
		new File(gemPath.gemFoldername).mkdirs()
		InputStream src = getClass().getResourceAsStream(GemPath.GEM_FILENAME)
		def destFile = new File(gemPath.gemPath)
		if (!destFile.exists()) {
			def dest = destFile.newDataOutputStream()
			dest << src
			src.close()
			dest.close()
		}
	}
}