package org.tjug.versus.scalaz

import java.util.Date

import org.tjug.versus.data.Approve

import scalaz._, Scalaz._

object FunctorExample {

  def main(args: Array[String]): Unit = {

    val approves: List[Approve] = List(new Approve(1, -1, new Date), new Approve(2, 1, new Date))

    val approveToValue: (Approve) => Integer = { approve: Approve => approve.getApproveValue }

    val cheatReviewValue: (Integer) => Integer = { value: Integer => 2 }

    val liftedApproveToValue: (List[Approve]) => List[Integer] = Functor[List].lift(approveToValue)

    val liftedCheatReviewValue: (List[Integer]) => List[Integer] = Functor[List].lift(cheatReviewValue)


    println(liftedApproveToValue.map(liftedCheatReviewValue).apply(approves))

    println()

    println(liftedCheatReviewValue.compose(liftedApproveToValue).apply(approves))


    val liftedBoth: (List[Approve]) => List[Integer] = Functor[List].lift(approveToValue.andThen(cheatReviewValue))

    println()

    println(liftedBoth.apply(approves))


    val composition = Functor[({type λ[Result] = Approve => Result})#λ].map(approveToValue)(cheatReviewValue)

    println()

    println(Functor[List].map(approves)(composition))

  }

}
