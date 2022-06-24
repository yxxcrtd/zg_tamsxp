 drop VIEW if EXISTS VIEW_FIN_PRODUCT_RATING_BREACH;

 CREATE  VIEW  VIEW_FIN_PRODUCT_RATING_BREACH  AS
  SELECT
    mbcr.o_code              AS OCODE,
    mbcr.o_type              AS OTYPE,
    mbcr.o_type AS O_TYPE1,
    NULL AS O_TYPE2,
    NULL AS ODISTYPE,
    '01' AS INNEROUTER,
    mbcr.grade_org_id        AS GRADEORGID,
    mbcr.grade_date          AS GRADEDATE,
    mbcr.notice_date AS NOTICEDATE,
    mbcr.short_long_term     AS SHORTLONGTERM,
    mbcr.grade_result        AS MSTRATINGRESULT01,
    mrm.grade_result_st      AS MSTRATINGRESULT02,
    mbcr.input_type          AS INPUTTYPE,
    mbcr.grade_expectation   AS GRADEEXPECTATION,
    mbcr.direction_change    AS DIRECTIONCHANGE,
    mbcr.issuer_rate_type    AS ISSUERRATETYPE
FROM
    mst_bond_credit_rating   mbcr
    LEFT JOIN mst_rating_match         mrm ON mbcr.grade_result = mrm.grade_result_or
UNION ALL
SELECT
    micr.o_code            AS ocode,
    micr.o_type            AS otype,
    NULL AS o_type1,
    micr.o_type AS o_type2,
    NULL AS odistype,
    '02' AS innerouter,
    micr.grade_org_id      AS gradeorgid,
    micr.grade_date        AS gradedate,
    NULL AS noticedate,
    NULL AS shortlongterm,
    micr.grade_result      AS mstratingresult01,
    mrm1.grade_result_st   AS mstratingresult02,
    micr.input_type        AS inputtype,
    NULL AS gradeexpectation,
    NULL AS directionchange,
    NULL AS issuerratetype
FROM
    mst_inner_credit_rating   micr
    LEFT JOIN mst_rating_match          mrm1 ON micr.grade_result = mrm1.grade_result_or
 ;
 
 
  drop VIEW if EXISTS VIEW_FIN_PRODUCT_RATING;

  CREATE  VIEW VIEW_FIN_PRODUCT_RATING AS 
  SELECT
    CASE
        WHEN mbcr.o_type = '02'
        THEN mbcr.o_code
        ELSE NULL
    END AS MARKETID,
    CASE
        WHEN mbcr.o_type = '01'
        THEN mbcr.o_code
        ELSE NULL
    END                    AS ORGID,
    mbcr.o_code            AS OCODE,
    mbcr.o_type            AS OTYPE,
    NULL                   AS ODISTYPE,
    '01'                   AS INNEROUTER,
    mbcr.grade_org_id      AS GRADEORGID,
    mbcr.grade_date        AS GRADEDATE,
    mbcr.notice_date       AS NOTICEDATE,
    mbcr.short_long_term   AS SHORTLONGTERM,
    mbcr.grade_result      AS MSTRATINGRESULT01,
    mrm.grade_result_st    AS MSTRATINGRESULT02,
    mbcr.input_type        AS INPUTTYPE,
    mbcr.GRADE_EXPECTATION AS GRADEEXPECTATION,
    mbcr.DIRECTION_CHANGE  AS DIRECTIONCHANGE,
    mbcr.issuer_rate_type  AS ISSUERRATETYPE
FROM
    mst_bond_credit_rating mbcr
LEFT JOIN
    mst_rating_match mrm
ON
    mbcr.grade_result = mrm.grade_result_or
UNION ALL
SELECT
    CASE
        WHEN micr.o_type = '02'
        THEN micr.o_code
        ELSE NULL
    END AS MARKETID,
    CASE
        WHEN micr.o_type = '01'
        THEN micr.o_code
        ELSE NULL
    END                  AS ORGID,
    micr.o_code          AS OCODE,
    micr.o_type          AS OTYPE,
    NULL                 AS ODISTYPE,
    '02'                 AS INNEROUTER,
    micr.grade_org_id    AS GRADEORGID,
    micr.grade_date      AS GRADEDATE,
    NULL                 AS NOTICEDATE,
    NULL                 AS SHORTLONGTERM,
    micr.grade_result    AS MSTRATINGRESULT01,
    mrm1.grade_result_st AS MSTRATINGRESULT02,
    micr.input_type      AS INPUTTYPE,
    NULL                 AS GRADEEXPECTATION,
    NULL                 AS DIRECTIONCHANGE,
    NULL                 AS ISSUERRATETYPE
FROM
    mst_inner_credit_rating micr
LEFT JOIN
    mst_rating_match mrm1
ON
    micr.grade_result = mrm1.grade_result_or
UNION ALL
SELECT DISTINCT
    fpr.finprod_market_id          AS MARKETID,
    ztratinginfo.ocode             AS ORGID,
    fpr.finprod_market_id          AS OCODE,
    '91'                           AS OTYPE,
    '91'                           AS ODISTYPE,
    ztratinginfo.innerouter        AS INNEROUTER,
    ztratinginfo.gradeorgid        AS GRADEORGID,
    ztratinginfo.gradedate         AS GRADEDATE,
    ztratinginfo.noticeDate        AS NOTICEDATE,
    ztratinginfo.shortlongterm     AS SHORTLONGTERM,
    ztratinginfo.mstRatingResult01 AS MSTRATINGRESULT01,
    ztratinginfo.mstRatingResult02 AS MSTRATINGRESULT02,
    ztratinginfo.inputtype         AS INPUTTYPE,
    ztratinginfo.gradeExpectation  AS GRADEEXPECTATION,
    ztratinginfo.directionChange   AS DIRECTIONCHANGE,
    ztratinginfo.issuerratetype    AS ISSUERRATETYPE
FROM
    fin_product fpr
INNER JOIN
    (
        SELECT
            *
        FROM
            VIEW_FIN_PRODUCT_RATING_BREACH
        WHERE
            o_type1 = '01'
        OR  o_type2 = '01' ) ztratinginfo
ON
    ztratinginfo.ocode = fpr.issuer
UNION ALL
SELECT DISTINCT
    fpr.finprod_market_id          AS MARKETID,
    ztratinginfo.ocode             AS ORGID,
    fpr.finprod_market_id          AS OCODE,
    '93'                           AS OTYPE,
    '93'                           AS ODISTYPE,
    ztratinginfo.innerouter        AS INNEROUTER,
    ztratinginfo.gradeorgid        AS GRADEORGID,
    ztratinginfo.gradedate         AS GRADEDATE,
    ztratinginfo.noticeDate        AS NOTICEDATE,
    ztratinginfo.shortlongterm     AS SHORTLONGTERM,
    ztratinginfo.mstRatingResult01 AS MSTRATINGRESULT01,
    ztratinginfo.mstRatingResult02 AS MSTRATINGRESULT02,
    ztratinginfo.inputtype         AS INPUTTYPE,
    ztratinginfo.gradeExpectation  AS GRADEEXPECTATION,
    ztratinginfo.directionChange   AS DIRECTIONCHANGE,
    ztratinginfo.issuerratetype    AS ISSUERRATETYPE
FROM
    fin_product fpr
INNER JOIN
    (
        SELECT
            *
        FROM
            VIEW_FIN_PRODUCT_RATING_BREACH
        WHERE
            o_type1 = '01'
        OR  o_type2 = '01' ) ztratinginfo
ON
    ztratinginfo.ocode = fpr.sponsor
UNION ALL
SELECT DISTINCT
    fpr.finprod_market_id          AS MARKETID,
    ztratinginfo.ocode             AS ORGID,
    fpr.finprod_market_id          AS OCODE,
    '92'                           AS OTYPE,
    '92'                           AS ODISTYPE,
    ztratinginfo.innerouter        AS INNEROUTER,
    ztratinginfo.gradeorgid        AS GRADEORGID,
    ztratinginfo.gradedate         AS GRADEDATE,
    ztratinginfo.noticeDate        AS NOTICEDATE,
    ztratinginfo.shortlongterm     AS SHORTLONGTERM,
    ztratinginfo.mstRatingResult01 AS MSTRATINGRESULT01,
    ztratinginfo.mstRatingResult02 AS MSTRATINGRESULT02,
    ztratinginfo.inputtype         AS INPUTTYPE,
    ztratinginfo.gradeExpectation  AS GRADEEXPECTATION,
    ztratinginfo.directionChange   AS DIRECTIONCHANGE,
    ztratinginfo.issuerratetype    AS ISSUERRATETYPE
FROM
    fin_product fpr
INNER JOIN
    (
        SELECT
            *
        FROM
            VIEW_FIN_PRODUCT_RATING_BREACH
        WHERE
            o_type1 = '01'
        OR  o_type2 = '01' ) ztratinginfo
ON
    ztratinginfo.ocode = fpr.financier
 
 
 
 ;
