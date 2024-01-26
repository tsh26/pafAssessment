package vttp2023.batch4.paf.assessment.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {

	// You may add additional dependency injections

	@Autowired
	private MongoTemplate template;

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred })
	 *
	 *
	 */
	// db.listings.aggregate([
	// 	{ $match: {address: {$regex: "australia", $options:"i "}}},
	// 	{
	// 		$project: {
	// 			_id: "$address.suburb"
	// 		}
	// 	}
	// 	]);

	public List<String> getSuburbs(String country) {
		MatchOperation matchName = Aggregation.match(
			Criteria.where("address").regex(country, "i")
		);

		ProjectionOperation projectSuburb = Aggregation.project("address.suburb");

		Aggregation aggregation = Aggregation.newAggregation(matchName, projectSuburb);

		AggregationResults<String> result = template.aggregate(aggregation, "listings", String.class);

		return result.getMappedResults();
}

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred })
	 *
	 *
	 */
	// db.listings.aggregate([
	// 	{ $match: {'address.suburb': {$regex: "Coogee", $options:"i"}}},
	// 	{ $match: {'price': {$lte: 10000} }},
	// 	{ $match: {'accommodates': {$gte: 1}}},
	// 	{ $match: {'min_nights': {$lte: 5}}},
	// 	{ $project: {_id:1, name:1, accommodates:1, price:1}},
	// 	{ $sort: {price:-1}}
	// 	]);

	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {
		MatchOperation matchSuburb = Aggregation.match(
			Criteria.where("address.suburb").regex(suburb, "i")
		);
		MatchOperation matchPriceRange = Aggregation.match(
			Criteria.where("price").lte(priceRange)
		);
		MatchOperation matchPersons = Aggregation.match(
			Criteria.where("accommodates").gte(persons)
		);
		MatchOperation matchDuration = Aggregation.match(
			Criteria.where("min_nights").lte(duration)
		);
		ProjectionOperation projectFields = Aggregation.project(
			"_id","name","accommodates","price"
		);
		SortOperation sortByPrice = Aggregation.sort(Direction.DESC, "price");

		Aggregation pipeline = Aggregation.newAggregation(matchSuburb, matchPriceRange,matchPersons,matchDuration,projectFields,sortByPrice);
		
		AggregationResults<AccommodationSummary> result = template.aggregate(pipeline, "listings", AccommodationSummary.class);

		return result.getMappedResults();
	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
