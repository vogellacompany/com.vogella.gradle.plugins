package com.vogella.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;
import org.gradle.tooling.model.GradleTask
import org.gradle.workers.WorkerExecutor

import java.nio.file.FileSystems;

import groovy.lang.MetaClass
import java.nio.file.Path

import javax.inject.Inject

class RenameFiles extends DefaultTask {

	public RenameFiles() {
		super()
		description = 'Rename file helper task'
		group = 'Documentation'
	}
	
	@TaskAction
	def renameFiles() {
		renameFiles(new File("${project.buildDir}"))
	}

	def renameFiles(def buildDir) {
		if (buildDir.exists()) {
			buildDir.eachFileRecurse( {
				if(it.name.startsWith('001_')){
					it.renameTo(new File(it.parent, it.name.substring(4, it.name.length())))
				}
			})
			
		}
	}
	
}

