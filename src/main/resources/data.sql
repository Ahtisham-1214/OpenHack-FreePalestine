INSERT INTO job (job, time)
SELECT seed.job, seed.time
FROM (
    SELECT 'Utility Bill' AS job, 1.5 AS time
    UNION ALL SELECT 'Open New Account', 4
    UNION ALL SELECT 'Deposit Cash', 1
    UNION ALL SELECT 'Cheque Withdrawal', 2
    UNION ALL SELECT 'Challan Pay', 3
    UNION ALL SELECT 'Delete Account', 0.5
) AS seed
WHERE NOT EXISTS (
    SELECT 1 FROM job AS existing_job WHERE existing_job.job = seed.job
);
