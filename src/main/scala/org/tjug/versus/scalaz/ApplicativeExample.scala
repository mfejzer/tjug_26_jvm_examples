package org.tjug.versus.scalaz

import java.util.Date

import org.tjug.versus.data.{Approve, Review, ReviewStatus}

import scala.collection.JavaConverters._
import scalaz.Scalaz._
import scalaz._

object ApplicativeExample {

  def main(args: Array[String]) {
    val approves: List[Approve] = List(new Approve(1, -1, new Date), new Approve(2, 1, new Date))

    val approveToValue: (Approve) => Integer = { approve: Approve => approve.getApproveValue }

    val cheatApproveToValue: (Approve) => Integer = { approve: Approve => 2 }

    val valueFunctions: List[(Approve) => Integer] = List(approveToValue, cheatApproveToValue)

    def execute = (approve: Approve, f: (Approve) => Integer) => f(approve)

    val executionResult: List[Integer] = Apply[List].lift2(execute).apply(approves, valueFunctions)

    println(executionResult)

    val firstReviewFiles = List("src/main/java/App.java", "build.gradle")
    val secondReviewFiles = List("src/main/scala/App.scala", "build.gradle")
    val files = List(firstReviewFiles, secondReviewFiles)

    def createReview = (approves: List[Approve], files: List[String], project: String) =>
      new Review(ReviewStatus.MERGED, approves.asJava, new Date, 1, project, new Date, files.asJava)

    val projectNames = List[String]("project1", "project2")
    val creationResult = Apply[List].lift3(createReview).apply(List(approves), files, projectNames)

    creationResult.foreach(review => {
      println(review.getProject)
      println(review.getFiles)
      println(review.getApproveHistory)
    })
  }
}
