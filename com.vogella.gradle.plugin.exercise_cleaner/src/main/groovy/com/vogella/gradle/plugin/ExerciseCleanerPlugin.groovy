package com.vogella.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ExerciseCleanerPlugin implements Plugin<Project>  {

	@Override
	public void apply(Project target) {
		target.task('clean_exercises', type: CleanExercises)
	}

}
