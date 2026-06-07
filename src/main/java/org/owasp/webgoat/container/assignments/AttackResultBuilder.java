/*
 * SPDX-FileCopyrightText: Copyright © 2024 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.assignments;

public class AttackResultBuilder {

  private enum ResultType {
    SUCCESS(true, "assignment.solved", true),
    FAILED(false, "assignment.not.solved", true),
    INFORMATION(false, null, false);

    private final boolean lessonCompleted;
    private final String defaultFeedback;
    private final boolean attemptWasMade;

    ResultType(boolean lessonCompleted, String defaultFeedback, boolean attemptWasMade) {
      this.lessonCompleted = lessonCompleted;
      this.defaultFeedback = defaultFeedback;
      this.attemptWasMade = attemptWasMade;
    }
  }

  private boolean lessonCompleted;
  private Object[] feedbackArgs;
  private String feedback;
  private String output;
  private Object[] outputArgs;
  private String assignment;
  private boolean attemptWasMade = false;

  public AttackResultBuilder assignmentCompleted(boolean lessonCompleted) {
    this.lessonCompleted = lessonCompleted;
    return this;
  }

  public AttackResultBuilder feedbackArgs(Object... args) {
    this.feedbackArgs = args;
    return this;
  }

  public AttackResultBuilder feedback(String feedback) {
    this.feedback = feedback;
    return this;
  }

  public AttackResultBuilder output(String output) {
    this.output = output;
    return this;
  }

  public AttackResultBuilder outputArgs(Object... args) {
    this.outputArgs = args;
    return this;
  }

  public AttackResultBuilder attemptWasMade() {
    return attemptWasMade(true);
  }

  public AttackResultBuilder attemptWasMade(boolean attemptWasMade) {
    this.attemptWasMade = attemptWasMade;
    return this;
  }

  public AttackResult build() {
    return new AttackResult(
        lessonCompleted, feedback, feedbackArgs, output, outputArgs, assignment, attemptWasMade);
  }

  AttackResult buildResolved() {
    return new AttackResult(lessonCompleted, feedback, output, assignment, attemptWasMade);
  }

  public AttackResultBuilder assignment(AssignmentEndpoint assignment) {
    return assignment(assignment.getClass().getSimpleName());
  }

  AttackResultBuilder assignment(String assignment) {
    this.assignment = assignment;
    return this;
  }

  private AttackResultBuilder resultType(ResultType resultType) {
    this.lessonCompleted = resultType.lessonCompleted;
    this.feedback = resultType.defaultFeedback;
    this.attemptWasMade = resultType.attemptWasMade;
    return this;
  }

  private static AttackResultBuilder builderFor(
      AssignmentEndpoint assignment, ResultType resultType) {
    return new AttackResultBuilder().assignment(assignment).resultType(resultType);
  }

  public static AttackResultBuilder copyOf(AttackResult attackResult) {
    return new AttackResultBuilder()
        .assignment(attackResult.getAssignment())
        .assignmentCompleted(attackResult.isLessonCompleted())
        .feedback(attackResult.getFeedback())
        .feedbackArgs(attackResult.getFeedbackArgs())
        .output(attackResult.getOutput())
        .outputArgs(attackResult.getOutputArgs())
        .attemptWasMade(attackResult.isAttemptWasMade());
  }

  /**
   * Convenience method for create a successful result:
   *
   * <p>- Assignment is set to solved - Feedback message is set to 'assignment.solved'
   *
   * <p>Of course, you can overwrite these values in a specific lesson
   *
   * @param assignment the assignment that was solved
   * @return a builder for creating a result from a lesson
   */
  public static AttackResultBuilder success(AssignmentEndpoint assignment) {
    return builderFor(assignment, ResultType.SUCCESS);
  }

  /**
   * Convenience method for create a failed result:
   *
   * <p>- Assignment is set to not solved - Feedback message is set to 'assignment.not.solved'
   *
   * <p>Of course, you can overwrite these values in a specific lesson
   *
   * @param assignment the assignment that was not solved
   * @return a builder for creating a result from a lesson
   */
  public static AttackResultBuilder failed(AssignmentEndpoint assignment) {
    return builderFor(assignment, ResultType.FAILED);
  }

  public static AttackResultBuilder informationMessage(AssignmentEndpoint assignment) {
    return builderFor(assignment, ResultType.INFORMATION);
  }
}
