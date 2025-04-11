/**
 * Problem: https://leetcode.com/discuss/post/124827/facebook-minimum-number-of-people-to-spr-ts1c/
 * <p>
 * Considering that I'ld would like to spread a promotion message across all people in twitter. Assuming the ideal case, if a person tweets a message, then every follower will re-tweet the message.
 *
 * You need to find the minimum number of people to reach out (for example, who doesn't follow anyone etc) so that your promotion message is spread out across entire network in twitter.
 *
 * Also, we need to consider loops like, if A follows B, B follows C, C follows D, D follows A (A -> B -> C -> D -> A) then reaching only one of them is sufficient to spread your message.
 *
 * Input: A 2x2 matrix like below. In this case, a follows b, b follows c, c follows a.
 *
 *     a b c
 * a  1 1 0
 * b  0 1 1
 * c  1 0 1
 * Output: minumum number of people to reach out
 * </p>
 *
 *
 */
 public class FacebookMinimumNumberIOfPeopleToSpreadAMessageTaraju {

}