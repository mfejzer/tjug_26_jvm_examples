package org.tjug.versus.scalaz

import java.util.{Arrays, Date}

import org.tjug.versus.data.{ReviewStatus, Review, Approve}

import scalaz._, Scalaz._

object EitherExample {

  def main(args: Array[String]) {
    val approve: Approve = new Approve(1, -1, new Date)

    val review: Review = new Review(ReviewStatus.MERGED, Arrays.asList(approve), new Date, 1, "project", new Date, Arrays.asList())

    val rightReview: Disjunction[String, Review] = review.right[String]

    println(rightReview.isRight)

    val toProjectNameLowercase: (Review) => String = { review: Review => "known project name" }

    val toDisjinctedProjectNameLowercase: (Review) => Disjunction[String, String] = { review: Review => review.getProject.toLowerCase.right[String] }

    val nameFilter: (String) => Boolean = { name: String => name == "known project name" }

    val unknownProjectError: Disjunction[String, Review] = "unknown project".left[Review]

    val something1: Disjunction[String, String] = rightReview.map(toProjectNameLowercase).filter(nameFilter)

    val something2: Disjunction[String, String] = rightReview.flatMap(toDisjinctedProjectNameLowercase).filter(nameFilter)

    println(something1.isRight)

    println(something2.isRight)

  }
}
