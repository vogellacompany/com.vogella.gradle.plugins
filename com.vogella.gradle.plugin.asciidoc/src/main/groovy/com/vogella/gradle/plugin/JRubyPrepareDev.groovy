package com.vogella.gradle.plugin

import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.TaskAction
import com.github.jrubygradle.JRubyPrepare

class JRubyPrepareDev extends JRubyPrepare {
	List<Gem> localDependencies = []

    public JRubyPrepareDev() {
		outputDir "${project.buildDir}/jruby_prepare"
		localDependency(new Gem(name: 'asciidoctor-pdf-1.5.0.alpha.17.dev.1', jrubyPrepare: this))
		doLast {
			copyDevGems(localDependencies)
			dependencies project.configurations.gems
			localDependencies.each {
				dependencies project.file(it.path)
			}
			copy()
		}
    }
	
	def localDependency(gem) {
		localDependencies.add(gem)
	}
	
	def copyDevGems(localDependencies) {
		localDependencies.each {
			//new File(it.folder).mkdirs()
			InputStream src = getClass().getResourceAsStream(it.fileName)
			project.file("${it.folder}").mkdirs()
			def destFile = project.file("${it.path}")
			println AsciiDoctorPlugin.LOG_PREFIX + "copying ${it.fileName} to ${destFile}"
			//destFile.mkdirs()
			if (destFile.exists()) {
				println AsciiDoctorPlugin.LOG_PREFIX + "${destFile} already exists"
				return
			}
			if (src) {
				def dest = destFile.newDataOutputStream()
				dest << src
				src.close()
				dest.close()
			} else {
				throw new FileNotFoundException("Gem copy failed. Couldn't find local gem resource: ${it.fileName}")
			}
		}
	}
	
	def String getGemFolder() {
		"${outputDir}/cache"
	}
}
