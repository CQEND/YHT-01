/*
 * SPDX-FileCopyrightText: Copyright © 2014 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.assignments;

import static org.apache.commons.text.StringEscapeUtils.escapeJson;

import lombok.Getter;
import org.owasp.webgoat.container.i18n.PluginMessages;

@Getter
public class AttackResult {

  private final boolean lessonCompleted;
  private final String feedback;
  private Object[] feedbackArgs;
  private final String output;
  private Object[] outputArgs;
  private final String assignment;
  private final boolean attemptWasMade;

  AttackResult(
      boolean lessonCompleted,
      String feedback,
      Object[] feedbackArgs,
      String output,
      Object[] outputArgs,
      String assignment,
      boolean attemptWasMade) {
    this.lessonCompleted = lessonCompleted;
    this.feedback = feedback;
    this.feedbackArgs = feedbackArgs;
    this.output = output;
    this.outputArgs = outputArgs;
    this.assignment = assignment;
    this.attemptWasMade = attemptWasMade;
  }

  public boolean assignmentSolved() {
    return lessonCompleted;
  }

  public AttackResult apply(PluginMessages pluginMessages) {
    return new AttackResultBuilder()
        .assignmentCompleted(lessonCompleted)
        .feedback(escapeJson(pluginMessages.getMessage(feedback, feedback, feedbackArgs)))
        .output(escapeJson(pluginMessages.getMessage(output, output, outputArgs)))
        .assignment(assignment)
        .attemptWasMade(attemptWasMade)
        .build();
  }
}
