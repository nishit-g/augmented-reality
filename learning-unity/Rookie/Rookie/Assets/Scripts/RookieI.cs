//  Topic --> Variable, Accessing the objects, if-else

// Initialize the cube to Red at start and when the user presses the Space button 
// increase the score. When the score is greater than 50 change the color to green


using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RookieI : MonoBehaviour
{

   
    //Create a gameObject for having a reference to change the color
    public GameObject cube;
    private int _score;

    void Start()
    {
        //Get the color component of the cube
        cube.GetComponent<Renderer>().material.color = Color.red;
        _score = 0;
    }

    void Update()
    {

        //Increase the score value everytime user presses the Space Key 
        if (Input.GetKeyDown(KeyCode.Space))
        {
            _score +=25;
        }

        //If the score is greater than 50 change the color to green of the gameObject
        if(_score > 50){
            cube.GetComponent<Renderer>().material.color = Color.green;
        }

    }
}
