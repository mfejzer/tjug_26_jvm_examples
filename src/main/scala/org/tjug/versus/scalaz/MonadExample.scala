package org.tjug.versus.scalaz

import java.util.{Arrays, Date}

import org.tjug.versus.data.{Approve, Review, ReviewStatus}

import scalaz.Scalaz._
import scalaz._

object MonadExample {
  def main(args: Array[String]) {
    val approve: Approve = new Approve(1, -1, new Date)

    val review: Review = new Review(ReviewStatus.MERGED,
      Arrays.asList(approve), new Date, 1, "project", new Date, Arrays.asList("src/main/java/App.java", "build.gradle"))

    val toProjectName: (Review) => Option[String] = (review: Review) => review.getProject.toUpperCase.some

    println(Monad[Option].point(review).>>=(toProjectName))
    println(Monad[Option].point(review).>>("something different".some))

    println()
  }
}
