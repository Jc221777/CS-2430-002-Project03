# CSIS 2430 — Project Plan (Plan + Trace)

Example for a team project (Projects 1–4). Update at least twice per project; for each "Done" task, include an evidence pointer.

## 1) Project Information

- Course / Term: CSIS 2430
- Project: Project 1 (Team)
- Team Name: Team 03
- Start Date: 10/26/2026
- Due Date: 11/13/2026
- Checkpoint Date (mid-project): 2026-02-13

## 2) Team & Roles (assigned for this project)

Roles are assigned per project and rotate between projects.

| Team Member       | Role (Implementation / Verification / Communications) | Primary Responsibilities (1–2 bullets)                                                       | Availability / Notes |
| ----------------- | ----------------------------------------------------- | -------------------------------------------------------------------------------------------- | -------------------- |
| Tristan Taj       | Implementation Lead                                   | Owns core algorithm implementation; integrates modules; maintains main branch health.        |                      |
| Thaddeus Schelp   | Verification Lead                                     | Creates test plan; edge cases; validates outputs; documents verification evidence.           |                      |
| Jasey Christensen | Communications Lead                                   | Assembles report/video; ensures run instructions; tracks deliverables; merges documentation. |                      |

## 3) Repository & Access (Required for Projects 1–4)

- Platform: GitHub
- Repository URL: <https://github.com/Jc221777/CS-2430-002-Project03.git>
- Instructor invited as viewer by checkpoint?: Yes — invited 10/26/2026

## 4) Milestones (Timeline)

| Milestone                           | Target Date | Owner            | Definition of Done                                                                                                           | Status (NS/IP/D) | Evidence Link / Pointer                                                                                                  |
| ----------------------------------- | ----------- | ---------------- | ---------------------------------------------------------------------------------------------------------------------------- | ---------------- | ------------------------------------------------------------------------------------------------------------------------ |
| Repo set up + skeleton running      | 10/26/2026  | Jasey            | Repo created; README run steps; sample I/O runs.                                                                             | D                | [**Commit #1f30263**](https://github.com/Jc221777/CS-2430-002-Project03/commit/1f3026385177e28a71c11fb4478bb7907019321b) |
| Implement 3 greedy strategies       | 11/02/2026  | Tristan          | Outputs experiments sorted by highest rated, lightest first, and best rating to weight ratio first.                          | D                | [PR#2](https://github.com/Jc221777/CS-2430-002-Project03/pull/2)                                                         |
| Implement Brute Force algorithm     | 11/02/2026  | Thaddeus         | Outputs the three highest rated subsets out of 4096 possible subsets                                                         | D                | [PR#1](https://github.com/Jc221777/CS-2430-002-Project03/pull/1)                                                         |
| Implement Comparison summary output | 11/09/2026  | Thaddues,Tristan | Add output summarizing all 3 greedy strategies, output of the bruteforce                                                     | IP               |                                                                                                                          |
| Implement change request            | 11/06/2026  | Thaddeus,Tristan | Add changes listed in the Project 3 change request .pdf                                                                      | IP               |                                                                                                                          |
| Implement Dynamic Programming       | 11/13/2026  | Thaddeus         | Implement a method or class that takes our problem and splits it into little problems that ultimately solve our main problem | IP               |                                                                                                                          |
| Complete team checkpoint            | 11/06/2026  | Jasey            | Complete all required items for the team checkpoint                                                                          | D                | [PR#7](https://github.com/Jc221777/CS-2430-002-Project03/pull/6)                                                         |
| Complete Team Video                 | 11/13/2026  | Team             | Film and edit video showcasing program                                                                                       | NS               |                                                                                                                          |
| Complete Team Report                | 11/13/2026  | Team             | Complete team report that answers questions provided in canvas rubric                                                        | NS               |                                                                                                                          |

## 5) Task Backlog (Assignments + Status)

| ID  | Task                                                    | Owner    | Due Date   | Status (NS/IP/D) | Evidence Link / Pointer                                                                                                  | Notes / Dependencies |
| --- | ------------------------------------------------------- | -------- | ---------- | ---------------- | ------------------------------------------------------------------------------------------------------------------------ | -------------------- |
| 1   | Create repository                                       | Jasey    | 10/26/2026 | D                | [**Commit #1f30263**](https://github.com/Jc221777/CS-2430-002-Project03/commit/1f3026385177e28a71c11fb4478bb7907019321b) | N/A                  |
| 2   | Implement algorithm for highest rating first            | Tristan  | 11/02/2026 | D                | [PR#2](https://github.com/Jc221777/CS-2430-002-Project03/pull/2)                                                         | N/A                  |
| 3   | Implement algorithm for lightest first                  | Tristan  | 11/02/2026 | D                | [PR#2](https://github.com/Jc221777/CS-2430-002-Project03/pull/2)                                                         | N/A                  |
| 4   | Implement Algorithm for ratio sorting                   | Tristan  | 11/02/2026 | D                | [PR#2](https://github.com/Jc221777/CS-2430-002-Project03/pull/2)                                                         | N/A                  |
| 5   | Implement Brute Force Algorithm                         | Thaddeus | 11/02/2026 | D                | [PR#1](https://github.com/Jc221777/CS-2430-002-Project03/pull/1)                                                         | N/A                  |
| 6   | Implement Junit test for the greedy strategies          | Thaddeus | 11/09/2026 | IP               | N/A                                                                                                                      | N/A                  |
| 7   | Implement Junit tests for brute force strategies        | Thaddues | 11/09/2026 | IP               | N/A                                                                                                                      | N/A                  |
| 8   | Complete project plan for project checkpoint            | Jasey    | 11/06/2026 | D                | [PR#7](https://github.com/Jc221777/CS-2430-002-Project03/pull/6)                                                         | N/A                  |
| 9   | Draft UML                                               | Jasey    | 11/06/2026 | D                | [PR#7](https://github.com/Jc221777/CS-2430-002-Project03/pull/6)                                                         | N/A                  |
| 10  | Design notes drafted                                    | Jasey    | 11/06/2026 | D                | [PR#7](https://github.com/Jc221777/CS-2430-002-Project03/pull/6)                                                         | N/A                  |
| 11  | Implement change request into the greedy strategies     | Tristan  | 11/09/2026 | IP               | N/A                                                                                                                      | N/A                  |
| 12  | Implement change request into the brute force algorithm | Thaddeus | 11/09/2026 | D                | [PR#5](https://github.com/Jc221777/CS-2430-002-Project03/pull/5)                                                         | N/A                  |
| 13  | Complete team report                                    | Team     | 11/13/2026 | NS               | N/A                                                                                                                      | N/A                  |
| 14  | Complete team video                                     | Team     | 11/13/2026 | NS               | N/A                                                                                                                      | N/A                  |
| 15  | Implement Dynamic Programming                           | Thaddeus | 11/13/2026 | IP               | N/A                                                                                                                      | N/A                  |

## 6) Verification Plan

Thaddeus will be building Junit tests to verify the edge cases and functionality of both the greedy strategies and the brute force strategies

## 7) Risks/Blockers

| Risk / Blocker       | Impact   | Mitigation Plan                                                             | Owner    | Status |
| -------------------- | -------- | --------------------------------------------------------------------------- | -------- | ------ |
| Making code testable | Med/High | Making sure our code is built in such a way that tests can be applied to it | Thaddeus | Active |
