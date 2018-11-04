PGPASSWORD=admin1
psql -h localhost -p 5432 -U tr_admin -d turysta -f places.sql 
psql -h localhost -p 5432 -U tr_admin -d turysta -f trails.sql 
psql -h localhost -p 5432 -U tr_admin -d turysta -f trail_points.sql 

