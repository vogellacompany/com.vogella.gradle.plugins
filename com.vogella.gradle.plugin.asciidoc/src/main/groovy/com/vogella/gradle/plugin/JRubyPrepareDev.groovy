package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction
import com.github.jrubygradle.JRubyPrepare

class JRubyPrepareDev extends JRubyPrepare {
	List<Gem> localDependencies = []

    public JRubyPrepareDev() {
		outputDir "${project.buildDir}/jruby_prepare"
		localDependency(new Gem(name: "asciidoctor-pdf-1.5.0.alpha.15.dev.2", jrubyPrepare: this))
		localDependency(new Gem(name: "asciidoctor-1.5.6.dev.1", jrubyPrepare: this))
		copyDevGems(localDependencies)
		dependencies project.configurations.gems
		localDependencies.each {
			dependencies project.file(it.path)
		}
    }
	
	def localDependency(gem) {
		localDependencies.add(gem)
	}
	
	def copyDevGems(localDependencies) {
		localDependencies.each {
			new File(it.folder).mkdirs()
			InputStream src = getClass().getResourceAsStream(it.fileName)
			def destFile = new File(it.path)
			if (src && !destFile.exists()) {
				def dest = destFile.newDataOutputStream()
				dest << src
				src.close()
				dest.close()
			}
		}
	}
	
	def String getGemFolder() {
		"${outputDir}/cache"
	}
}
