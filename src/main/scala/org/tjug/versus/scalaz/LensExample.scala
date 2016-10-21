package org.tjug.versus.scalaz

import java.util.{Arrays, Date}

import scala.collection.JavaConverters._

import org.tjug.versus.data.{ReviewStatus, Review, Approve}

import scalaz.Lens

class LensExample {

  def main(args: Array[String]) {

    val approveValueLens = Lens.lensu[Approve, Integer](
      (approve, newValue) => new Approve(approve.getUserId, newValue, approve.getGrantDate),
      approve => approve.getApproveValue
    )

    val firstApproveLens = Lens.lensu[List[Approve], Approve](
      (oldApproveList, newApprove) => newApprove :: oldApproveList.drop(1)
      ,
      list => list(0)
    )

    val reviewApprovesLens = Lens.lensu[Review, List[Approve]](
      (review, newApproveHistory) => new Review(
        review.getReviewStatus(),
        newApproveHistory.asJava,
        review.getSubmitDate(),
        review.getChangeId(),
        review.getProject(),
        review.getCloseDate(),
        review.getFiles()
      ),
      review => review.getApproveHistory().asScala.toList
    )

    val reviewApproveValueLens = approveValueLens.compose(firstApproveLens.compose(reviewApprovesLens))


    val approve: Approve = new Approve(1, -1, new Date)

    val review: Review = new Review(ReviewStatus.MERGED, Arrays.asList(approve), new Date, 1, "project", new Date, Arrays.asList())

    println(reviewApproveValueLens.get(review))
    val newReview = reviewApproveValueLens.set(review, 2)
    println(reviewApproveValueLens.get(newReview))
  }

}
