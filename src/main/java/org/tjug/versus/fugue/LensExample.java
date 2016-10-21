package org.tjug.versus.fugue;

import io.atlassian.fugue.optic.Lens;
import org.tjug.versus.data.Approve;
import org.tjug.versus.data.Review;
import org.tjug.versus.data.ReviewStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LensExample {


    public static void main(String[] args) {

        Lens<Approve, Integer> approveValueLens = Lens.lens(Approve::getApproveValue,
                newApproveValue -> oldApprove -> new Approve(oldApprove.getUserId(), newApproveValue, oldApprove.getGrantDate())
        );

        Lens<List<Approve>, Approve> firstApproveLens = Lens.lens(list -> list.get(0),
                newApprove -> oldApproveList -> {
                    List<Approve> result = new ArrayList<>();
                    result.add(newApprove);
                    result.addAll(oldApproveList.subList(1, oldApproveList.size()));
                    return result;
                }
        );

        Lens<Review, List<Approve>> reviewApprovesLens = Lens.lens(Review::getApproveHistory,
                approveHistory -> review ->
                        new Review(review.getReviewStatus(),
                                approveHistory,
                                review.getSubmitDate(),
                                review.getChangeId(),
                                review.getProject(),
                                review.getCloseDate(),
                                review.getFiles())
        );

        Lens<List<Approve>, Integer> firstApproveValueLens = firstApproveLens.composeLens(approveValueLens);

        Lens<Review, Integer> reviewApproveValueLens = reviewApprovesLens.composeLens(firstApproveValueLens);

        Approve approve = new Approve(1, -1, new Date());

        Review review = new Review(ReviewStatus.MERGED,
                Arrays.asList(approve),
                new Date(),
                1,
                "project",
                new Date(),
                new ArrayList<>()
                );

        Review newReview = reviewApproveValueLens.set(2).apply(review);
        System.out.println(review.getApproveHistory().get(0).getApproveValue());
        System.out.println(newReview.getApproveHistory().get(0).getApproveValue());


    }
}
