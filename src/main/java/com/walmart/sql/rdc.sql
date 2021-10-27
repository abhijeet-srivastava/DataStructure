-- RDC OB
SELECT WM_YR_WK_ID, CAST(REFRESH_DATE AS DATE) AS REFRESH_DATE, cast(CALENDAR_DATE as date) as calendar_date, DC_NBR,
                   (CASE WHEN (CHANNEL_MTHD_CD = '5' AND ORIGIN ='RDC')
OR (CHANNEL_MTHD_CD = '2' AND ORIGIN ='RDC')
THEN 'STAPLE' ELSE 'DISTRIBUTION'
END) AS CHANNEL,
(CASE WHEN  VNPK_CSPK_CD <> 'B' AND CONVEYABLE_IND = 'Y' THEN 'CONVEYABLE'
WHEN  VNPK_CSPK_CD <> 'B' AND CONVEYABLE_IND = 'N' THEN 'NON-CONVEYABLE'
ELSE 'BREAKPACK'
END) AS PACK_TYPE,
SOURCE_SYSTEM,SUM(planned_cases_qty) AS CASES_FCST, SUM(PLANNED_PICKS_QTY) AS PICKS_FCST

-- RDC IB
select dc_nbr,wm_yr_wk_id,CHANNEL,sum(planned_arv_vnpk_qty) as fcst, refresh_date
from
(select dc_nbr,wm_yr_wk_id,
case when channel_mthd_subtype in('STAPLE') then 'STAPLE'
when channel_mthd_subtype in('XDOCK-DA') then 'XDOCK-DA'
when channel_mthd_subtype in('XDOCK') and origin in('IDC') then 'IDC'
when channel_mthd_subtype in('XDOCK') and origin in('FDC') then 'FDC'