package com.vogella.gradle.plugin

class TaskUtil {
	
	def static topProject(project) {
		project.parent ? project.parent : project
	}
	
}
