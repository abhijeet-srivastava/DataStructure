CREATE TABLE platform_dq_abhi.rdc_ib_plan_extents_subset AS
WITH rdc_plans AS
  (SELECT refresh_date,
          datediff(calendar_date, refresh_date) AS days_out,
          dc_nbr,
          case when channel_mthd_subtype in('STAPLE') then 'STAPLE'
                when channel_mthd_subtype in('XDOCK-DA') then 'XDOCK-DA'
                when channel_mthd_subtype in('XDOCK') and origin in('IDC') then 'IDC'
                when channel_mthd_subtype in('XDOCK') and origin in('FDC') then 'FDC'
                END AS channel_mthd_desc
   FROM ip_us.rdc_ib_grs_plans_summary
   where refresh_date >= '2020-09-25'
   GROUP BY refresh_date,
            calendar_date,
            dc_nbr,
            case when channel_mthd_subtype in('STAPLE') then 'STAPLE'
                when channel_mthd_subtype in('XDOCK-DA') then 'XDOCK-DA'
                when channel_mthd_subtype in('XDOCK') and origin in('IDC') then 'IDC'
                when channel_mthd_subtype in('XDOCK') and origin in('FDC') then 'FDC'
                END)
SELECT refresh_date,
       dc_nbr,
       channel_mthd_desc,
       min(days_out) AS min_days_out,
       max(days_out) AS max_days_out,
       count(DISTINCT days_out) AS coverage
FROM rdc_plans
GROUP BY refresh_date,
         dc_nbr,
         channel_mthd_desc;


CREATE TABLE platform_dq_abhi.rdc_ob_plan_extents_subset AS
WITH rdc_ob_plans AS
  (SELECT refresh_date,
          datediff(calendar_date, refresh_date) AS days_out,
          dc_nbr,
          (CASE WHEN (CHANNEL_MTHD_CODE = '5' AND ORIGIN ='RDC') OR (CHANNEL_MTHD_CODE = '2' AND ORIGIN ='RDC')
                THEN 'STAPLE'
            ELSE 'DISTRIBUTION'
           END) AS channel_mthd_desc,
          (CASE WHEN  VNPK_CSPK_CODE <> 'B' AND CONVEYABLE_IND = 'Y'
                THEN 'CONVEYABLE'
            WHEN  VNPK_CSPK_CODE <> 'B' AND CONVEYABLE_IND = 'N'
                THEN 'NON-CONVEYABLE'
            ELSE 'BREAKPACK'
            END) AS channel_mthd_subtype
   FROM ip_us.rdc_ob_grs_plans_master
   where refresh_date >= '2020-09-25'
   GROUP BY refresh_date,
            calendar_date,
            dc_nbr,
            (CASE WHEN (CHANNEL_MTHD_CODE = '5' AND ORIGIN ='RDC') OR (CHANNEL_MTHD_CODE = '2' AND ORIGIN ='RDC')
                THEN 'STAPLE'
            ELSE 'DISTRIBUTION'
            END),
            (CASE WHEN  VNPK_CSPK_CODE <> 'B' AND CONVEYABLE_IND = 'Y'
                THEN 'CONVEYABLE'
            WHEN  VNPK_CSPK_CODE <> 'B' AND CONVEYABLE_IND = 'N'
                THEN 'NON-CONVEYABLE'
            ELSE 'BREAKPACK'
            END))
SELECT refresh_date,
       dc_nbr,
       channel_mthd_desc,
       channel_mthd_subtype,
       min(days_out) AS min_days_out,
       max(days_out) AS max_days_out,
       count(DISTINCT days_out) AS coverage
FROM rdc_ob_plans
GROUP BY refresh_date,
         dc_nbr,
         channel_mthd_desc,
         channel_mthd_subtype;


CREATE TABLE platform_dq_abhi.gdc_ib_plan_extents_subset AS
WITH gdc_ib_plans AS
  (SELECT refresh_date,
          datediff(calendar_date, refresh_date) AS days_out,
          chamber,
          dc_nbr,
          base_div_nbr,
          (CASE
                WHEN whse_area_code = '1' THEN 'MEAT'
                WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
                WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
                WHEN  whse_area_code = '6' THEN 'DRY'
                WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
          END) AS warehouse_area
   FROM ip_us.gdc_ib_grs_plans_summary
   where refresh_date >= '2020-09-25'
   GROUP BY refresh_date,
            calendar_date,
            dc_nbr,
            base_div_nbr,
            chamber,
            (CASE
                WHEN whse_area_code = '1' THEN 'MEAT'
                WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
                WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
                WHEN  whse_area_code = '6' THEN 'DRY'
                WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
          END))
SELECT refresh_date,
       dc_nbr,
       base_div_nbr as volume_type,
       chamber,
       warehouse_area,
       min(days_out) AS min_days_out,
       max(days_out) AS max_days_out,
       count(DISTINCT days_out) AS coverage
FROM gdc_ib_plans
GROUP BY refresh_date,
         dc_nbr,
         base_div_nbr,
         chamber,
         warehouse_area;


CREATE TABLE platform_dq_abhi.gdc_ob_plan_extents_subset AS
WITH gdc_ob_plans AS
  (SELECT refresh_date,
          datediff(calendar_date, refresh_date) AS days_out,
          dc_nbr,
          base_div_nbr,
          chamber,
          (CASE
                WHEN whse_area_code = '1' THEN 'MEAT'
                WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
                WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
                WHEN  whse_area_code = '6' THEN 'DRY'
                WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
          END) AS warehouse_area
   FROM ip_us.gdc_ob_grs_plans_summary
   where refresh_date >= '2020-09-25'
   GROUP BY refresh_date,
            calendar_date,
            dc_nbr,
            base_div_nbr,
            chamber,
            (CASE
                WHEN whse_area_code = '1' THEN 'MEAT'
                WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
                WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
                WHEN  whse_area_code = '6' THEN 'DRY'
                WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
          END))
SELECT refresh_date,
       dc_nbr,
       base_div_nbr as volume_type,
       chamber,
       warehouse_area,
       min(days_out) AS min_days_out,
       max(days_out) AS max_days_out,
       count(DISTINCT days_out) AS coverage
FROM gdc_ob_plans
GROUP BY refresh_date,
         dc_nbr,
         base_div_nbr,
         chamber,
         warehouse_area;


----------------------------------------------------------------------
create table gdc_ib_catg_level as
with gdc_ib_chamber_level as
(SELECT refresh_date,
     calendar_date,
     dc_nbr,
     base_div_nbr,
      chamber,
      (CASE
            WHEN whse_area_code = '1' THEN 'MEAT'
            WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
            WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
            WHEN  whse_area_code = '6' THEN 'DRY'
            WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
            else 'UNKNOWN'
      END) AS warehouse_area,
      sum(fcst) as cases
FROM svc_flowct.gdc_dly_dc_ib_plans_summary_bfd
GROUP BY refresh_date,
        calendar_date,
        dc_nbr,
        base_div_nbr,
        chamber,
        (CASE
            WHEN whse_area_code = '1' THEN 'MEAT'
            WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
            WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
            WHEN  whse_area_code = '6' THEN 'DRY'
            WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
            else 'UNKNOWN'
      END))
select refresh_date,
   calendar_date,
   dc_nbr,
   base_div_nbr,
   chamber,
   warehouse_area,
   sum(cases) as cases
from gdc_ib_chamber_level
GROUP BY
   refresh_date,
   calendar_date,
   dc_nbr,
   base_div_nbr,
   chamber,
   warehouse_area

----------------------------------------------------------------------
create table gdc_ob_catg_level as
with gdc_ob_chamber_level as
(SELECT refresh_date,
     calendar_date,
     dc_nbr,
     base_div_nbr,
      chamber,
      (CASE
            WHEN whse_area_code = '1' THEN 'MEAT'
            WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
            WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
            WHEN  whse_area_code = '6' THEN 'DRY'
            WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
            else 'UNKNOWN'
      END) AS warehouse_area,
      sum(fcst) as cases
FROM svc_flowct.gdc_dly_dc_ob_plans_summary_bfd
GROUP BY refresh_date,
        calendar_date,
        dc_nbr,
        base_div_nbr,
        chamber,
        (CASE
            WHEN whse_area_code = '1' THEN 'MEAT'
            WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
            WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
            WHEN  whse_area_code = '6' THEN 'DRY'
            WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
            else 'UNKNOWN'
      END))
select refresh_date,
   calendar_date,
   dc_nbr,
   base_div_nbr,
   chamber,
   warehouse_area,
   sum(cases) as cases
from gdc_ob_chamber_level
GROUP BY
   refresh_date,
   calendar_date,
   dc_nbr,
   base_div_nbr,
   chamber,
   warehouse_area


-----------------------

create table gdc_ob_catg_level_staging as
with gdc_ib_chamber_level as
(SELECT refresh_date,
     calendar_date,
     dc_nbr,
     base_div_nbr,
      chamber,
      (CASE
            WHEN whse_area_code = '1' THEN 'MEAT'
            WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
            WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
            WHEN  whse_area_code = '6' THEN 'DRY'
            WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
            else 'UNKNOWN'
      END) AS warehouse_area,
      sum(forecast) as cases
FROM ip_us.gdc_ob_grs_plans_summary
GROUP BY refresh_date,
        calendar_date,
        dc_nbr,
        base_div_nbr,
        chamber,
        (CASE
            WHEN whse_area_code = '1' THEN 'MEAT'
            WHEN (whse_area_code = '2') OR (whse_area_code = '3') THEN 'DAIRY'
            WHEN (whse_area_code = '4') OR (whse_area_code = '5') THEN 'FROZEN'
            WHEN  whse_area_code = '6' THEN 'DRY'
            WHEN (whse_area_code = '7') OR (whse_area_code = '8') OR (whse_area_code = '9') THEN 'PRODUCE'
            else 'UNKNOWN'
      END))
select refresh_date,
   calendar_date,
   dc_nbr,
   base_div_nbr,
   chamber,
   warehouse_area,
   sum(cases) as cases
from gdc_ib_chamber_level
GROUP BY
   refresh_date,
   calendar_date,
   dc_nbr,
   base_div_nbr,
   chamber,
   warehouse_area

-----------------------------------------

CREATE TABLE platform_dq_abhi.rdc_ib_chamber_level_staging AS
WITH rdc_plans AS
  (SELECT refresh_date,
          calendar_date,
          dc_nbr,
          datediff(calendar_date, refresh_date) AS days_out,
          dc_nbr,
          case when channel_mthd_subtype in('STAPLE') then 'STAPLE'
                when channel_mthd_subtype in('XDOCK-DA') then 'XDOCK-DA'
                when channel_mthd_subtype in('XDOCK') and origin in('IDC') then 'IDC'
                when channel_mthd_subtype in('XDOCK') and origin in('FDC') then 'FDC'
                END AS channel_mthd_desc
   FROM ip_us.rdc_ib_plan_summary
   GROUP BY refresh_date,
            calendar_date,
            dc_nbr,
            case when channel_mthd_subtype in('STAPLE') then 'STAPLE'
                when channel_mthd_subtype in('XDOCK-DA') then 'XDOCK-DA'
                when channel_mthd_subtype in('XDOCK') and origin in('IDC') then 'IDC'
                when channel_mthd_subtype in('XDOCK') and origin in('FDC') then 'FDC'
                END)
SELECT refresh_date,
       dc_nbr,
       channel_mthd_desc,
       min(days_out) AS min_days_out,
       max(days_out) AS max_days_out,
       count(DISTINCT days_out) AS coverage
FROM rdc_plans
GROUP BY refresh_date,
         dc_nbr,
         channel_mthd_desc;