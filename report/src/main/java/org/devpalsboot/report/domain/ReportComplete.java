package org.devpalsboot.report.domain;

import org.devpalsboot.report.ReportStatus;
public record ReportComplete (String objectPath, ReportStatus status)
{
}
